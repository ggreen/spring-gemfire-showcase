
# Proxy versus Cache Proxy

````shell
create region --name=Location --type=PARTITION
````

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
java -jar  applications/account-location-rest-service/target/account-location-rest-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```


```kotlin
@RestController
class LocationController(private var locationRepository: LocationRepository) {
    @PostMapping("locations/location")
    fun saveLocation(@RequestBody location: Location) {
        locationRepository.save(location)
    }

    @GetMapping("locations/location/{id}")
    fun findLocation(@PathVariable id: String): Location? {
        var results = locationRepository.findById(id)
        if(results.isEmpty)
            return null

        return results.get()
    }
}
```

```shell
list clients
```


```shell
open http://localhost:8081
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



