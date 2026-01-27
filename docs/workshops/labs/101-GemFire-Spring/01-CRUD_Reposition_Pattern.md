# CRUD Reposition Pattern

Start GemFire Locator and Server

```shell
deployments/local/scripts/podman/labs/start-gemfire-10-2.sh
```


## Gfsh

Start the GemFire command line shell ("Gfsh")

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

The following is example code for the Account Controller.

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

The following is the example Account Repository.

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

Open Swagger UI

```shell
open http://localhost:6001/swagger-ui/index.html
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

Post Account Data

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

Post Account Data

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

Get account 1

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```

Get account 2

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/2' \
  -H 'accept: */*';echo
```

Get account 3

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/2' \
  -H 'accept: */*';echo
```


---------------------

# Query


Post Account

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


In gfsh



```shell
query --query="select * from /Account where name = 'Account 99' "
```

```shell
query --query="select distinct name from /Account"
```

----------------

# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```