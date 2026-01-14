# Cache Listener Workshop

This workshop demonstrates how to use a Cache Listener to listen for changes in a GemFire region and log those changes to a file.

Prerequisite Download Apps (one time only)

```shell
mkdir -p runtime/apps
wget -P runtime/apps https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Event-Streaming-2025/cache-listener-audit-0.0.1.jar
wget -P runtime/apps https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Event-Streaming-2025/cq-listener-audit-0.0.1.jar
```

Start GemFire Locator and Server

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


Create region in GemFire

```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=Employees --type=PARTITION --enable-statistics=true"
```

Auditor Application

See [cq-listener-audit](../../../../applications/listener/client/cq-listener-audit)

```java
@Component
@Slf4j
public class CqAuditListener extends CqListenerAdapter implements CqListener {
    public void onEvent(CqEvent cqEvent) {
        log.info("AUDIT: CQ Event - key: {} newValue: {} cq: {}",
                cqEvent.getKey(),cqEvent.getNewValue(),cqEvent.getCq());
    }
}
```

Configuration 

```java
@Slf4j
@Configuration
@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true)
public class GemFireConfig {


    @Value("${spring.application.name:cq-listener-audit}")
    private String cqName;

    @Value("${audit.cq.oql}")
    private String oql;

    @Value("${gemfire.cq.durable:true}")
    private boolean durable;

    @Bean
    CqQuery cqQuery(ClientCache clientCache,CqAuditListener cqAuditListener) throws CqException, CqExistsException, RegionNotFoundException
    {
            var queryService = clientCache.getQueryService();
            // Create CqAttribute using CqAttributeFactory
            CqAttributesFactory cqf = new CqAttributesFactory();

            // Create a listener and add it to the CQ attributes callback defined below
            cqf.addCqListener(cqAuditListener);
            var cqa = cqf.create();
            // Name of the CQ and its query

            // Create the CqQuery
            var cqQuery = queryService.newCq(cqName, oql, cqa,durable);

            // Execute CQ, getting the optional initial result set
            // Without the initial result set, the call is priceTracker.execute();
            cqQuery.execute();

            return cqQuery;

    }
```

Start CQ Auditor Application

```shell
mkdir -p runtime/logs
java -jar runtime/apps/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /Employees" --logging.file.name=runtime/logs/audit-cq.log
```

Also Tail File in separate terminal

```shell
tail -f runtime/logs/audit-cq.log
```

**Insert Employee 1**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","employeeId":"E001","department":"Engineering","salary":85000}' http://localhost:7080/gemfire-api/v1/Employees/E001
```

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Jill","lastName":"Smith","employeeId":"E002","department":"Engineering","salary":65000}' http://localhost:7080/gemfire-api/v1/Employees/E002
```

View tail/log output


Start Another Auditor Application

```shell
mkdir -p runtime/logs
java -jar runtime/apps/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /Employees where salary > 1000000" --spring.application.name="emp.1m.plus" --logging.file.name=runtime/logs/audit-1m.log
```

Submit non-matching employ infor

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Keshya","lastName":"Williams","employeeId":"R914","department":"Engineering","salary":40000}' http://localhost:7080/gemfire-api/v1/Employees/R914
```


Submit match employ

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Richie","lastName":"Rich","employeeId":"R001","department":"Engineering","salary":1000100}' http://localhost:7080/gemfire-api/v1/Employees/E002
```

View tail/log output

---------------
## Cleanup


- Stop all apps

Shutdown GemfFire
```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "shutdown --include-locators"
```
