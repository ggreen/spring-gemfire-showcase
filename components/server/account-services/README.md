mvn install



Setup Database

```sql
CREATE TABLE IF NOT EXISTS account (
	id varchar(100) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id)
);

```


cd components/server/account-services/

```shell
cd $GEMFIRE_HOME/bin
```

Start GemFire server

```shell
./gfsh -e "start locator --name=locator"
```

Configure Pdx

```shell
./gfsh -e "connect" -e "configure pdx --read-serialized=true --disk-store --auto-serializable-classes=.*"
```

Start Cache Server
```shell
./gfsh -e "start server --name=server1 --locators=localhost[10334]  --J=-Dconfig.properties=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/components/server/account-services/src/main/resources/server.properties"
```

Deploy JAR

```shell
./gfsh -e "connect" -e "deploy --jar=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/components/server/account-services/target/account-services-0.0.1-SNAPSHOT.jar"
```

```shell
./gfsh -e "connect" -e "list deployed"
```

```shell
./gfsh -e "connect" -e "list deployed"
```

```shell
./gfsh -e "connect" -e "create async-event-queue --id=accountQueue --listener=spring.gemfire.showcase.account.server.listeners.AccountAsyncEventListener"
```

```shell
./gfsh -e "connect" -e "create region --type=PARTITION --name=Account --async-event-queue-id=accountQueue"
```


```shell
./gfsh -e "connect" -e "shutdown"
```