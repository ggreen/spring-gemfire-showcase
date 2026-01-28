# GemFire Transactions


Start GemFire cluster with 1 locator and 3 servers

```shell
deployments/local/scripts/podman/labs/start-gemfire-10-2-mult-servers.sh
```


Open Gfsh

```shell
podman exec -it gf-locator gfsh
```

In GemFire connect to the cluster

```gfsh
connect
```

Create Co-located Regions

```shell
create region --name=Account --type=PARTITION
create region --name=Location --type=PARTITION --colocated-with=Account
```

Start Account Service

```shell
java -jar runtime/apps/account-location-service-1.0.0.jar --server.port=6001 --spring.data.gemfire.pool.locators="localhost[10334]"
```

Post Account Data


```shell
curl -X 'POST' \
  'http://localhost:6001/accountLocations' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
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
  },
  "location" : {
    "address" : "123 Straight Street",
    "city" : "Jersey City",
    "stateCode" : "NJ",
    "zipCode" : "55555"
  }
}';echo 
```

Get Data 

```shell
curl -X 'GET' \
  'http://localhost:6001/accountLocations/1' \
  -H 'accept: */*';echo
```

Update Account Phone

```shell
curl -X 'POST' \
  'http://localhost:6001/accountLocations' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
    "id": "1",
    "name": "Joe Doe - Account",
    "customer" :
    {
      "firstName" : "Jane",
      "lastName" : "Doe",
      "contact": {
        "email" : "jdoe@gemfire.dev",
        "phone" : "555-111-1111"
      }
    }
  },
  "location" : {
    "address" : "123 Straight Street",
    "city" : "Jersey City",
    "stateCode" : "NJ",
    "zipCode" : "55555"
  }
}';echo 
```

Get Data

```shell
curl -X 'GET' \
  'http://localhost:6001/accountLocations/1' \
  -H 'accept: */*';echo
```

Update Account and Location (EXCEPTION rollback)


```shell
curl -X 'POST' \
  'http://localhost:6001/accountLocations' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "account": {
    "id": "1",
    "name": "Joe Doe - Account",
    "customer" :
    {
      "firstName" : "Jane",
      "lastName" : "Doe",
      "contact": {
        "email" : "jdoe@gemfire.dev",
        "phone" : "555-666-6666"
      }
    }
  },
  "location" : {
    "address" : "123 Straight Street",
    "city" : "Jersey City",
    "stateCode" : "NA",
    "zipCode" : "INVALID"
  }
}';echo 
```

RollBacked data

```shell
curl -X 'GET' \
  'http://localhost:6001/accountLocations/1' \
  -H 'accept: */*';echo
```
# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```
