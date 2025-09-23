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
````

Gfsh Create region

```shell
create region --name=FavoriteAccounts --type=PARTITION_REDUNDANT --enable-statistics=true --entry-idle-time-expiration=30 --entry-idle-time-expiration-action=DESTROY
```




Create Text Field

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "create lucene index --name=FavoriteAccountsNameIndex --region=/FavoriteAccounts --field=name"
```


Start Web Application(s)


```shell
java -jar applications/web/account-web-app/target/account-web-app-0.0.2-SNAPSHOT.jar --server.port=8001
```


```shell

```

```shell
java -jar applications/web/account-web-app/target/account-web-app-0.0.2-SNAPSHOT.jar --spring.application.name=account2 --server.port=8002
```

```shell
java -jar applications/web/account-web-app/target/account-web-app-0.0.2-SNAPSHOT.jar --spring.application.name=account3 --server.port=8003
```


```shell
for i in {1..30}; do
  echo "POST $i"
curl -X 'POST' \
  'http://localhost:8001/session/account' -u user:password \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "string",
  "name": "string",
  "customer": {
    "firstName": "string",
    "lastName": "string",
    "contact": {
      "email": "string",
      "phone": "string"
    }
  }
}'
curl -X 'POST' \
  'http://localhost:8002/session/account' -u user:password \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "string",
  "name": "string",
  "customer": {
    "firstName": "string",
    "lastName": "string",
    "contact": {
      "email": "string",
      "phone": "string"
    }
  }
}'
curl -X 'POST' \
  'http://localhost:8003/session/account' -u user:password \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "string",
  "name": "string",
  "customer": {
    "firstName": "string",
    "lastName": "string",
    "contact": {
      "email": "string",
      "phone": "string"
    }
  }
}'

sleep 10
done
```