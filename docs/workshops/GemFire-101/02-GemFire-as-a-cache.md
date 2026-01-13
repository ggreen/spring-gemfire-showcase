# GemFire as a cache



```java
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDataService implements AccountService{
    private final AccountRepository accountJdbcRepository;

    @CacheEvict(value = {"AccountDbCache"}, key = "#account.id")
    @Override
    public Account save(Account account) {
        return accountJdbcRepository.save(account);
    }

    @Cacheable(value = {"AccountDbCache"})
    @Override
    public Account findByAccountId(String id) {
        log.info("!!!!==============SEARCHING for Account Id:{}",id);
        var optional = accountJdbcRepository.findById(id);
        return optional.orElse(null);

    }
}
```
Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```

Open Gfsh

```shell
podman exec -it gf-locator gfsh
```

```gfsh
connect
```

```gfsh
create region --name=AccountDbCache --entry-time-to-live-expiration=30 --enable-statistics=true --type=PARTITION
```

Start Postgres

```shell
podman run -it --rm --name postgres \
  -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_HOST_AUTH_METHOD=trust postgres:15.15
```




Start service

```shell
java -jar applications/service/account-jdbc-caching-rest-service/target/account-jdbc-caching-rest-service-1.0.0.jar
```

--logging.level.org.springframework.data=DEBUG --logging.level.org.apache.geode=DEBUG --logging.level.org.springframework.cache=DEBUG

```shell
list clients
```


```shell
podman exec -it postgres psql -U postgres
```

```psql
insert into gf_cache.accounts(id,name) 
values 
('1','Account 1'),
('2','Account 2'),
('3','Account 3'),
('4','Account 4'),
('5','Account 5');
```



```shell
open http://localhost:6003
```

Load cache
```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*'
```


Read from cache
```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*';echo
```

Read from cache
```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/2' \
  -H 'accept: */*';echo
```

In Gfsh

```gfsh
query --query="select * from /AccountDbCache"
```

Evict cache

```shell
curl -w "\n Total Time:    %{time_total}s\n"  -X 'POST' \
  'http://localhost:6003/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Account Updated"
}'
```


Clears cache because system of record change

```shell
query --query="select * from /AccountDbCache.keys"
```

Reload cache from relationship database
```shell
curl  -w "\n Total Time:    %{time_total}s\n"  -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*';echo
```

```shell
query --query="select * from /AccountDbCache.keys"
```

------------------------------

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


---------------------------------------------

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

--------------------
# GemFire operational data store

```shell
destroy region --name=/Location
destroy region --name=/Account
create region --name=Account --type=PARTITION_REDUNDANT_PERSISTENT  --startup-recovery-delay=1000
create region --name=Location --type=PARTITION_REDUNDANT_PERSISTENT  --startup-recovery-delay=1000 --colocated-with=/Account
```


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
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```

In gfsh

```shell
stop server --name=server1
list members
start server --name=server1  --server-port=50001
```


```shell
query --query="select * from /Account where name = 'Account' "
```


```shell
start server --name=server2  --server-port=50002
rebalance
list members
stop server --name=server1
```


```shell
query --query="select * from /Account where name = 'Account' "
```


```shell
start server --name=server1  --server-port=50001
start server --name=server3  --server-port=50003
list members
query --query="select * from /Account where name = 'Account' "
stop server --name=server2
list members
rebalance
query --query="select * from /Account where name = 'Account' "
stop server --name=server1
list members
query --query="select * from /Account where name = 'Account' "
```
------------------------
# Bonus - listener


````java
@Component
class VMwareAccountListener(private val locationGemFireTemplate: GemfireTemplate) {
    private val log : Logger = LogManager.getLogger(VMwareAccountListener::class.java)

    @ContinuousQuery(name = "addVMwareLocation", query = "select * from /Account where name = 'VMware' ")
    fun addVMwareLocation(cqEvent: CqEvent) {
        log.info("==============Adding location!!!!!")

        var key = cqEvent.key.toString()
        val location : Location = Location()
        location.id = key
        location.address = "3401 Hillview Ave"
        location.city = "Palo Alto"
        location.stateCode = "CA"
        location.zipCode = "94304"

        locationGemFireTemplate.put(key,location)
    }
}
````

```java
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-geode-showcase
```

Gfsh

```shell
query --query="select * from /Location"
```

```java
java -Dspring.profiles.active=local -jar  applications/account-location-event-service/target/account-location-event-service-0.0.1-SNAPSHOT.jar
```

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "2021",
  "name": "VMware"
}'
```

```shell
query --query="select * from /Location"
```

Expected added "3401 Hillview Ave"

**********************


# Cleanup

```text
remove --region=Account --key=1;
remove --region=Account --key=2;
remove --region=Account --key=3;
remove --region=Account --key=99;
remove --region=Account --key=100;
query --query="select * from /Account.keys"
```