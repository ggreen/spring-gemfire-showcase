# Proxy versus Cache Proxy

Start GemFire

```shell
deployments/local/scripts/podman/labs/start-gemfire-10-2.sh
```

Open Gfsh

```shell
podman exec -it gf-locator gfsh
```

In GemFire connect to the cluster

```gfsh
connect
```

Create a GemFire region

````gfsh
create region --name=Account --type=PARTITION
create region --name=Location --type=PARTITION
````

Run Application

```shell
java -jar  applications/service/account-location-service/target/account-location-service-1.0.0.jar
```


```java
@RequestMapping("accountLocations")
@RequiredArgsConstructor
public class AccountLocationController {
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final String validZipRegEx = "^\\d{5}(?:[-\\s]\\d{4})?$";

    @PostMapping
    public void save(@RequestBody AccountLocation accountLocation) {

        var location = accountLocation.getLocation();

        accountRepository.save(accountLocation.getAccount());

        if(!location.getZipCode().matches(validZipRegEx)) {
            throw new IllegalArgumentException("Invalid zip code "+location.getZipCode());
        }

        locationRepository.save(accountLocation.getLocation());
    }
}
```

List clients in Gfsh

```gfsh
list clients
```


```shell
open http://localhost:8081
```

```shell
curl -X 'POST' \
  'http://localhost:8081/accountLocations' \
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


```shell
curl -X 'GET' \
  'http://localhost:8081/locations/location/1' \
  -H 'accept: */*';echo
```

Register Interest

```java
    @Bean("Location")
    ClientRegionFactoryBean<String, Location> location(GemFireCache gemfireCache)
    {
        Interest[] interests = {new Interest(".*")};
        var bean= new ClientRegionFactoryBean<String,Location>();
        bean.setCache(gemfireCache);
        bean.setDataPolicy(DataPolicy.NORMAL);
        bean.setInterests(interests);
        return bean;
    }

```

In gfsh

```shell
shutdown
list members
```

```shell
curl -X 'GET' \
  'http://localhost:8081/locations/location/1' \
  -H 'accept: */*';echo
```


In gfsh

```shell
start server --name=server1  --server-port=50001
list members
```

```shell
curl -X 'GET' \
  'http://localhost:8081/locations/location/1' \
  -H 'accept: */*';echo
```

Empty (local updated)



