# Lookup Aside Caching Pattern

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

```gfsh
create region --name=AccountDbCache --entry-time-to-live-expiration=300 --enable-statistics=true --type=PARTITION
```

Start Postgres

```shell
podman run -it --rm --name postgres \
  -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_HOST_AUTH_METHOD=trust postgres:15.15
```


Start service

```shell
java -jar runtime/apps/account-jdbc-caching-rest-service-1.0.0.jar
```



Adding Data to Postgres


Open Psql

```shell
podman exec -it postgres psql -U postgres
```

Insert Account Data

```psql
insert into gf_cache.accounts(id,name) 
values 
('1','Account 1'),
('2','Account 2'),
('3','Account 3'),
('4','Account 4'),
('5','Account 5');
```


Open Spring App

```shell
open http://localhost:6003
```
Click Click Swagger UI and Post account




Or use the following Curl command to access data

```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*'
```


Second Read from cache will be faster

```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*';echo
```

Read an additional account record

```shell
curl -w "\n Total Time:    %{time_total}s\n" -X 'GET' \
  'http://localhost:6003/accounts/2' \
  -H 'accept: */*';echo
```

Select loaded cache data in GemFire using Gfsh

```gfsh
query --query="select * from /AccountDbCache"
```

Evict cache using Spring Cache

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

Query in Gfsh (Account was evicted)

```shell
query --query="select * from /AccountDbCache"
```

Reload data from database

```shell
curl  -w "\n Total Time:    %{time_total}s\n"  -X 'GET' \
  'http://localhost:6003/accounts/1' \
  -H 'accept: */*';echo
```

Query in Gfsh 

```shell
query --query="select * from /AccountDbCache"
```


Data is Also evicted after 5 minutes of activity based in 

See the following setting in the above create region statement

``` 
--entry-time-to-live-expiration=300 
``` 
------------------------------

# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```