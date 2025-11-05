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

Start Auditor Application

```shell
mkdir -p runtime/logs
java -jar runtime/apps/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /Employees where salary > 1000000" --spring.application.name="emp.1m.plus" --logging.file.name=runtime/logs/audit-1m.log
```

Submit non-match employ

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Keshya","lastName":"Williams","employeeId":"R914","department":"Engineering","salary":40000}' http://localhost:7080/gemfire-api/v1/Employees/R914
```


Submit match employ

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"Richie","lastName":"Rich","employeeId":"R001","department":"Engineering","salary":1000100}' http://localhost:7080/gemfire-api/v1/Employees/E002
```
