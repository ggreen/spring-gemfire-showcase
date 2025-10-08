# Event Persistence with Cache Listener

This workshop demonstrates how to use a event for Cache Listener and Cache Listener are persisted to prevent message lost.


Start GemFire Locator and Server

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


Create region in GemFire

```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=EmployeesPersisted --type=PARTITION_PERSISTENT --enable-statistics=true"
```


-------------

Start Cache Lister Auditor Application

Durable message expiration is 1 hour = 10800 seconds

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cache-listener-audit/target/cache-listener-audit-0.0.1.jar --audit.region.name=EmployeesPersisted --audit.region.key.reg.expression=".*" --spring.data.gemfire.pool.default.locators="localhost[10334]" --spring.data.gemfire.cache.client.durable-client-id=employeeAduitor --spring.data.gemfire.cache.client.durable-client-timeout=10800 --logging.file.name=runtime/logs/audit.log
```

**Insert Employee 1**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Sally","lastName":"John","employeeId":"PC001","department":"Engineering","salary":85500}' http://localhost:7080/gemfire-api/v1/EmployeesPersisted/PC001
```

Stop Cache Lister Auditor Application (Ctrl-C) then restart to see events that were missed

**Insert Employee 2**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Peter","lastName":"James","employeeId":"PC002","department":"Engineering","salary":85200}' http://localhost:7080/gemfire-api/v1/EmployeesPersisted/PC002
```

Start Application again to see missed events

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cache-listener-audit/target/cache-listener-audit-0.0.1.jar --audit.region.name=EmployeesPersisted --audit.region.key.reg.expression=".*" --spring.data.gemfire.pool.default.locators="localhost[10334]" --spring.data.gemfire.cache.client.durable-client-id=employeeAduitor --spring.data.gemfire.cache.client.durable-client-timeout=10800 --logging.file.name=runtime/logs/audit.log --spring.data.gemfire.pool.subscription-redundancy=1
```

-------------

Start CQ Lister Auditor Application Hold events for 1 hour 10800

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cq-listener-audit/target/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /EmployeesPersisted" --logging.file.name=runtime/logs/audit-cq.log --spring.data.gemfire.cache.client.durable-client-id=cq-listener-audit --spring.data.gemfire.cache.client.durable-client-timeout=10800 --spring.data.gemfire.pool.subscription-redundancy=1
```

**Insert Employee 1**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","employeeId":"P001","department":"Engineering","salary":85000}' http://localhost:7080/gemfire-api/v1/EmployeesPersisted/P001
```

Stop CQ Lister Auditor Application (Ctrl-C) then restart to see events that were missed

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Jill","lastName":"Smith","employeeId":"P002","department":"Engineering","salary":85000}' http://localhost:7080/gemfire-api/v1/EmployeesPersisted/P002
```

Start CQ Lister Auditor Application Hold events for 1 hour 10800

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cq-listener-audit/target/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /EmployeesPersisted" --logging.file.name=runtime/logs/audit-cq.log --spring.data.gemfire.cache.client.durable-client-id=cq-listener-audit --spring.data.gemfire.cache.client.durable-client-timeout=10800 --spring.data.gemfire.pool.subscription-redundancy=1
````


Verify events were received from when the application was down
