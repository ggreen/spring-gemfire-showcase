# Getting Started


Start GemFire Cluster

```shell
./deployments/local/scripts/gemfire/start.sh
```

Create the session region

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=ClusteredSpringSessions --type=PARTITION_REDUNDANT"
```



```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create region --name=FavoriteAccounts --type=PARTITION_REDUNDANT --enable-statistics=true --entry-time-to-live-expiration=30 --entry-time-to-live-expiration-action=DESTROY"


Gfsh Create region

```shell
create region --name=FavoriteAccounts --type=PARTITION_REDUNDANT --enable-statistics=true --entry-idle-time-expiration=30 --entry-idle-time-expiration-action=DESTROY
```


Create Text Field

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create lucene index --name=FavoriteAccountsNameIndex --region=/FavoriteAccounts --field=name"
```



```shell
java -jar applications/account-web-app/target/account-web-app-0.0.1-SNAPSHOT.jar
```