# account-batch

# Demo Instructions

## RabbitMQ


```shell
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

```shell
kubectl apply -f https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/tanzu-rabbitmq-event-streaming-showcase/main/deployment/cloud/k8/data-services/rabbitmq/rabbitmq-3-node.yml
```

## Postgres

```shell
./deployments/cloud/k8/dataServices/postgres/tanzu-postgres-operator-setup.sh
```

```shell
kubectl apply -f deployments/cloud/k8/dataServices/postgres/postgres.yml
```

--------------------

## GemFire

In a new shell -> Run GemFire Locator
```
./deployments/cloud/k8/dataServices/gemfire/gf-k8-setup.sh
```

 Create GemFire Cluster

```shell
k apply -f deployments/cloud/k8/dataServices/gemfire/gemfire.yml
```

In a new shell -> Setup GemFire Regions

```shell
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect --locator=gemfire1-locator-0.gemfire1-locator.default.svc.cluster.local[10334]" -e "create region --name=Account --type=PARTITION"
```

Install SCF

```shell
./deployments/cloud/k8/dataServices/scdf/install_scdf_external_postgres.sh
```

-------------------

# GemFire Management Console (Gideon Console)

```shell
kubectl apply -f deployments/cloud/k8/dataServices/gemfire/gideonConsole/gemfire-management-console.yml
```

Open Management Console


1 - Click -> Connect

2 -  Enter Connection Setting

- Enter cluster nickname: gemfire
- Host: gf-locator
- Port: 7070

Then  connect to cluster

3 - Use the Data Explore to view the loaded customer data

--------

# Spring Cloud DataFlow

The following is an example DSL to run the batch in Spring Cloud DataFlow


Access SCDF Dashboard

Click Add -> Import application from properties


```properties
task.account-batch=docker:cloudnativedata/account-batch:0.0.1-SNAPSHOT
```

```shell
account-batch --db.schema=cache_accounts --spring.data.gemfire.pool.default.locators="gemfire1-locator-0.gemfire1-locator.default.svc.cluster.local[10334]" --batch.jdbc.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --spring.sql.init.platform="postgres" --spring.liquibase.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --batch.load.accounts="true" --account.data.count="500"  --account.data.batch.size="50"
```


--spring.liquibase.user=postgres
--batch.jdbc.username=postgres
batch.jdbc.password
spring.liquibase.password


```shell
deployer.account-batch.kubernetes.secretKeyRefs=[{envVarName: 'spring.liquibase.user', secretName: 'postgres-db-app-user-db-secret', dataKey: 'username'}, {envVarName: 'batch.jdbc.username', secretName: 'postgres-db-app-user-db-secret', dataKey: 'username'},{envVarName: 'batch.jdbc.password', secretName: 'postgres-db-app-user-db-secret', dataKey: 'password'},{envVarName: 'spring.liquibase.password', secretName: 'postgres-db-app-user-db-secret', dataKey: 'password'}]
```

![scdf-task.png](docs/imgs/scdf-task.png)


--------------------------------------
## Docker building image

The following are the steps to build a docker image
```shell
mvn install
cd applications/account-batch
mvn spring-boot:build-image
```

Example for tagging and pushing to docker hub
```shell
docker tag account-batch:0.0.1-SNAPSHOT cloudnativedata/account-batch:0.0.1-SNAPSHOT
docker push cloudnativedata/account-batch:0.0.1-SNAPSHOT
```

--------------------------



