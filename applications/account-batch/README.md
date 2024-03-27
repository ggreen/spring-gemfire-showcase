# Spring Batch + GemFire

Example running standalone

```shell
java -jar applications/account-batch/target/account-batch-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres --db.schema=cache_accounts --spring.data.gemfire.pool.default.locators="localhost[10334]" --batch.jdbc.url="jdbc:postgresql://localhost:5432/postgres"  --batch.jdbc.username=postgres --spring.sql.init.platform=postgres
```


Example running in Spring Cloud DataFlow
```shell
account-batch --db.schema=cache_accounts --spring.data.gemfire.pool.default.locators="localhost[10334]" --batch.jdbc.url="jdbc:postgresql://localhost:5432/postgres"  --batch.jdbc.username=postgres --spring.sql.init.platform=postgres
```
