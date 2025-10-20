

Getting Started

```shell
java -jar applications/listener/client/cq-listener-audit/target/cq-listener-audit-0.0.1.jar  --spring.data.gemfire.pool.default.locators="localhost[10334]" --audit.cq.oql="select * from /EmployeesPersisted" --logging.file.name=runtime/logs/audit-cq.log --spring.data.gemfire.cache.client.durable-client-id=cq-listener-audit --spring.data.gemfire.cache.client.durable-client-timeout=10800 --spring.data.gemfire.pool.subscription-redundancy=1
```