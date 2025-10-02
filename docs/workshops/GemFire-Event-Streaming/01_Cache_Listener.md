# Cache Listener Workshop

This workshop demonstrates how to use a Cache Listener to listen for changes in a GemFire region and log those changes to a file.

Start GemFire Locator and Server

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


Create region in GemFire

```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=Employees --type=PARTITION --enable-statistics=true"
```


Start Auditor Application

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cache-listener-audit/target/cache-listener-audit-0.0.1.jar --audit.region.name=Employees --spring.data.gemfire.pool.default.locators="localhost[10334]" --spring.data.gemfire.cache.client.durable-client-id=employeeAduitor --logging.file.name=runtime/logs/audit.log
```

Also Tail File in separate terminal

```shell
tail -f runtime/logs/audit.log
```

**Insert Employee 1**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","employeeId":"E001","department":"Engineering","salary":85000}' http://localhost:7080/gemfire-api/v1/Employees/E001
```

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Jill","lastName":"Smith","employeeId":"E002","department":"Engineering","salary":65000}' http://localhost:7080/gemfire-api/v1/Employees/E002
```

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Mark","lastName":"Johnson","employeeId":"E003","department":"Engineering","salary":95000}' http://localhost:7080/gemfire-api/v1/Employees/E003
```

----------------------------


Start Another Cache Listener Application

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cache-listener-audit/target/cache-listener-audit-0.0.1.jar --audit.region.name=Employees --spring.data.gemfire.pool.default.locators="localhost[10334]" --server.port=0 --spring.data.gemfire.cache.client.durable-client-id=employeeAduitor-2 --logging.file.name=runtime/logs/audit-2.log
```


Also Tail File in separate terminal

```shell
tail -f runtime/logs/audit-2.log
```

Submit Another Employee

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Jill","lastName":"Smith","employeeId":"E062","department":"Engineering","salary":65000}' http://localhost:7080/gemfire-api/v1/Employees/E006
```

--------------------------------

Filtering Events by Keys


Start Auditor Application

```shell
mkdir -p runtime/logs
java -jar applications/listener/client/cache-listener-audit/target/cache-listener-audit-0.0.1.jar --audit.region.name=Employees --spring.application.name=employeeAduitorForDs --server.port=0 --spring.data.gemfire.pool.default.locators="localhost[10334]" --spring.data.gemfire.cache.client.durable-client-id="\${spring.application.name}" --audit.region.key.reg.expression="^D.*" --logging.file.name=runtime/logs/audit-ds.log
```


Submit  Employee - Ignoore

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Ignored","lastName":"Ignored","employeeId":"C006","department":"Engineering","salary":65000}' http://localhost:7080/gemfire-api/v1/Employees/C006
```

Submit  Employee - Detected

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Sue","lastName":"Detected","employeeId":"D006","department":"Engineering","salary":65000}' http://localhost:7080/gemfire-api/v1/Employees/D006
```