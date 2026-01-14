# Start Clustering

Start GemFire Locator and Server

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


## Gfsh

```shell
podman exec -it gf-locator gfsh
```

In Gfsh

Connect to Locator

```gfsh
connect --locator=gf-locator[10334]
```

Create Account Region

```gfsh
create region --name=Account --type=PARTITION
```

Start Account REST App


```java
@AllArgsConstructor
@RestController
@RequestMapping("accounts")
public class AccountController
{
  private final AccountRepository accountRepository;
  private final AccountNameToUpperCase accountNameToUpperCase;

  @PostMapping
  public <S extends Account> S save(@RequestBody S account)
  {
    return accountRepository.save(account);
  }

  @GetMapping("{id}")
  public Account findById(@PathVariable String id)
  {
    return accountRepository.findById(id).orElse(null);
  }

  @DeleteMapping("{id}")
  public void deleteById(@PathVariable String id)
  {
    accountRepository.deleteById(id);
  }


  @GetMapping("names/{name}")
  public List<Account> findByName(@PathVariable String name) {
    return accountRepository.findByNameContaining(name);
  }

  @GetMapping("paging/{pageNumber}/{pageSize}")
  public List<Account> findAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
    return accountRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
  }


  @PostMapping("name/like")
  public List<Account> findFirst2ByNameLikeOrderByByName(@RequestBody String nameLike){
    ScrollPosition offset = ScrollPosition.keyset();
    return accountRepository.findFirst2ByNameLikeOrderByName(nameLike, offset).getContent();
  }

  @PutMapping("functions/upperCase/name/{accountId}")
  public Account toUpperCaseName(@PathVariable String accountId) {
    return this.accountNameToUpperCase.toUpperCaseName(accountId);
  }
}
```

```java
@Repository
public interface AccountRepository
extends CrudRepository<Account,String>
{
}
```

Start account-service

```shell
java -jar runtime/apps/account-service-1.0.0.jar --server.port=6001 --spring.data.gemfire.pool.locators="localhost[10334]"
```

Save Account

```shell
open http://localhost:6001
```

Post Account Data

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "Joe Doe - Account",
  "customer" :  
    {
       "firstName" : "Jane",
       "lastName" : "Doe",
       "contact": {
        "email" : "jdoe@gemfire.dev",
        "phone" : "555-555-5551"
       }
    }
}';echo 
```


```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "2",
  "name": "John Matthew - Account",
  "customer" :  
    {
       "firstName" : "John",
       "lastName" : "Matthew",
       "contact": {
        "email" : "jMatthew@gemfire.dev",
        "phone" : "555-555-5552"
       }
    }
}';echo 
```


```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "3",
  "name": "Josiah Imani",
  "customer" :  
    {
       "firstName" : "Josiah",
       "lastName" : "Imani",
       "contact": {
        "email" : "jImani@gemfire.dev",
        "phone" : "555-555-5553"
       }
    }
}';echo 
```

Get account 

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/2' \
  -H 'accept: */*';echo
```

Get account Expected: {"id":"1","name":"Account"}

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```

---------------------

# Query


In gfsh


```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "99",
  "name": "Account 99"
}';echo 
```

```shell
query --query="select * from /Account where name = 'Account 99' "
```

```shell
query --query="select distinct name from /Account"
```

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "100",
  "name": "Account 100"
}';echo 
```

Using like

```shell
query --query="select * from /Account where name like '%100%'"
```



Using set

```shell
query --query="select distinct name from /Account where id in SET('2','3')"
```

Using Object Methods

```shell
query --query="select distinct name, name.length() as len from /Account"
```

```shell
query --query="select count(*) from /Account where name.length() > 7"
```

```shell
create index --name=accountName --expression=name --region=/Account
```

```shell
query --query="<trace> select * from /Account where name = 'Account 99'"
```
----------------

# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```