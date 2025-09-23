Clean any previous Cluster Configurations

```shell
destroy region --name=/AccountEvents
destroy async-event-queue --id=springAeq
```

```shell
deploy --jar=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/components/server/account-listeners/target/account-listeners-0.0.1-SNAPSHOT.jar


create async-event-queue --id=springAeq --listener=spring.gemfire.showcase.account.server.account.listeners.AccountAsyncEventListener

create region --name=AccountEvents --async-event-queue-id=springAeq --type=PARTITION

put --key=1 --value=1 --region=/AccountEvents

show log --member=server1
```