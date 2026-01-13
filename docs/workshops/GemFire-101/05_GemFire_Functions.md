
# GemFire functions

In gfsh

```shell script
deploy --jar=/Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase/components/account-functions/target/account-functions-0.0.1-SNAPSHOT.jar
```

```shell
list functions
```

```shell
destroy region --name=/Location
destroy region --name=/Account
create region --name=Account --type=PARTITION
create region --name=Location --type=PARTITION --colocated-with=/Account
```



Restart account-location-rest-service and account-rest-jdbc

Because of registered interest

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Account"
}'
```


```shell
curl -X 'POST' \
  'http://localhost:8081/locations/location' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "address": "123 street",
  "city": "NYC",
  "stateCode": "NY",
  "zipCode": "21277"
}'
```

gfsh

```java
public class AccountCountInNyFunction implements Function<PdxInstance>, Declarable
{
    public void execute(FunctionContext<PdxInstance> functionContext)
    {
        
        Query query = queryService.newQuery(
        "select count(*) as cnt from /Account a, /Location l where a.id = l.id and l.stateCode = 'NY'");

        try {

             Collection<Object> results = (Collection<Object>) query.execute(rfc);
             
          ...    
```


```shell
execute function --id=AccountCountInNyFunction --region=/Account
```

Expected 1

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "2",
  "name": "Account2 NYC"
}'
```


```shell
curl -X 'POST' \
  'http://localhost:8081/locations/location' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "2",
  "address": "2 street",
  "city": "NYC",
  "stateCode": "NY",
  "zipCode": "21277"
}'
```

```shell
execute function --id=AccountCountInNyFunction --region=/Account
```

Expected 2