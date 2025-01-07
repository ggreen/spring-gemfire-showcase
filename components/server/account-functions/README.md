# GemFire Function

This is an example of executing
GemFire functions with Spring Data for GemFire.


Spring greatly simplifies the example of GemFire 
function. Developers can define an interface 
where the input and output matches the server-side
GemFire function definition.
The developer determine which function and 
how it is executed using annotations.

The following is an example Client Side Interface to
execute a function with id="AccountNameToUpperCase"
on a region named "Account".

```Java
@OnRegion(region = "Account")
public interface AccountNameToUpperCase {

    @FunctionId("AccountNameToUpperCase")
    Account toUpperCaseName(String accountId);
}
```


The following is the server-side GemFire function.

```Java
public class AccountNameToUpperCaseFunction implements Function<Object[]> {

            private Logger logger = LogManager.getLogger(spring.gemfire.showcase.account.server.function.AccountNameToUpperCaseFunction.class);


            @Override
            public void execute(FunctionContext<Object[]> functionContext) {

                var rfc = (RegionFunctionContext<Object[]>)functionContext ;
                Region<String,Object> region =  rfc.getDataSet();

                var accountId = valueOf(functionContext.getArguments()[0]);
                var accountObject = region.get(accountId);

                if(accountObject  == null)
                    return;

                Account account;

                if(accountObject instanceof PdxInstance accountPdx)
                    account = (Account)accountPdx.getObject();
                else
                    account = (Account)accountObject;

                logger.info("account {}",account);

                account.setName(account.getName().toUpperCase());

                functionContext.getResultSender().lastResult(account);

    }

    @Override
    public String getId() {
        return "AccountNameToUpperCase";
    }
}
```

## Setup

Use Maven 3.9.1 and Java 17 to build the components

```shell
mvn package
```

Start GemFire Cluster

```shell
./deployments/local/scripts/gemfire/start.sh
```


Deploy the GemFire function JAR

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "deploy --jar=components/server/account-functions/target/account-functions-0.0.1-SNAPSHOT.jar"
```

Confirm the JAR is Deployed

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "list deployed"
```

Confirm the Function registered

```shell
$GEMFIRE_HOME/bin/gfsh -e "connect" -e "list functions"
```
-----------------
# Testing

Start Spring Account Application Service

```shell
java -jar  applications/account-service/target/account-service-*.jar
```

Post Account Data

```shell
curl -X 'POST' \
  'http://localhost:6001/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "1",
  "name": "josiah doe"
}'
```

Call Function to convert name to upper

```shell
curl -X 'PUT' \
  'http://localhost:6001/accounts/functions/upperCase/name/1' \
  -H 'accept: */*'
```