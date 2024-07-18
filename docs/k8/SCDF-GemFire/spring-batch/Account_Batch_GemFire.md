# account-batch

The following outline how to test the Account Batch
Spring Batch job in Spring Cloud DataFlow using on Kubernetes engines
such as GKE.


# Setup Instructions

Install Spring Cloud DataFlow requirements (RabbitMQ & Postgres).

## RabbitMQ

Install  RabbitMQ Cluster Operator

```shell
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

Deploy RabbitMQ Cluster
```shell
kubectl apply -f https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/tanzu-rabbitmq-event-streaming-showcase/main/deployment/cloud/k8/data-services/rabbitmq/rabbitmq-3-node.yml
```

## Postgres

Install  Postgres Kubernetes Operator

```shell
./deployments/cloud/k8/dataServices/postgres/tanzu-postgres-operator-setup.sh
```

Deploy Postgres Database

```shell
kubectl apply -f deployments/cloud/k8/dataServices/postgres/postgres.yml
```

--------------------

## GemFire

Install GemFire Kubernetes Operator
```
./deployments/cloud/k8/dataServices/gemfire/gf-k8-setup.sh
```

 Deployment GemFire Cluster

```shell
kubectl apply -f deployments/cloud/k8/dataServices/gemfire/gemfire.yml
```

Create GemFire Regions

```shell
kubectl  exec -it gemfire1-locator-0 -- gfsh -e "connect --locator=gemfire1-locator-0.gemfire1-locator.default.svc.cluster.local[10334]" -e "create region --name=Account --type=PARTITION"
```

--------------------

## SCDF

Install Spring Cloud DataFlow

```shell
./deployments/cloud/k8/dataServices/scdf/install_scdf_external_postgres.sh
```


--------

# Spring Cloud DataFlow

The following is an example DSL to run the batch in Spring Cloud DataFlow


Access Spring Cloud DataFlow Dashboard

- Click  Applications -> Add Application
- Client "Register one or more applications"
- Name: account-batch
- Type: task
- Spring Boot Version: 3.x
- URI: docker:cloudnativedata/account-batch:0.0.1-SNAPSHOT


## Launch Task

- Click Tasks/Job
- Create Task


Copy the following
```shell
account-batch --db.schema=cache_accounts --spring.data.gemfire.pool.default.locators="gemfire1-locator-0.gemfire1-locator.default.svc.cluster.local[10334]" --batch.jdbc.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --spring.sql.init.platform="postgres" --spring.liquibase.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --batch.load.accounts="true" --account.data.count="500"  --account.data.batch.size="50"
```

```shell
account-batch --db.schema=cache_accounts --batch.jdbc.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --spring.sql.init.platform="postgres" --spring.liquibase.url="jdbc:postgresql://postgres-db:5432/postgres-db"  --batch.load.accounts="true" --account.data.count="500"  --account.data.batch.size="50"
```


Set the database credentials from GemFire ConfigMap

- spring.liquibase.user=postgres
- batch.jdbc.username=postgres
- batch.jdbc.password
- spring.liquibase.password


```shell
deployer.account-batch.kubernetes.secretKeyRefs=[{envVarName: 'spring.liquibase.user', secretName: 'postgres-db-app-user-db-secret', dataKey: 'username'}, {envVarName: 'batch.jdbc.username', secretName: 'postgres-db-app-user-db-secret', dataKey: 'username'},{envVarName: 'batch.jdbc.password', secretName: 'postgres-db-app-user-db-secret', dataKey: 'password'},{envVarName: 'spring.liquibase.password', secretName: 'postgres-db-app-user-db-secret', dataKey: 'password'}]
deployer.account-batch.kubernetes.configMapKeyRefs=[{envVarName: 'spring.data.gemfire.pool.default.locators', configMapName: 'gemfire1-config', dataKey: 'locators'}]
```

![scdf-task.png](docs/imgs/scdf-task.png)

-------------------

## GemFire Management Console (Gideon Console)

Install GMC

```shell
kubectl apply -f deployments/cloud/k8/dataServices/gemfire/gideonConsole/gemfire-management-console.yml
```

Open Management Console


1 - Click -> Connect

2 -  Enter Connection Setting

- Enter cluster nickname: gemfire
- Host: gemfire1-locator
- Port: 7070

Then  connect to cluster

3 - Use the Data Explore to view the loaded Account data
