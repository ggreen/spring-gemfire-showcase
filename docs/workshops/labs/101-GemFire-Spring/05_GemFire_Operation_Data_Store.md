# GemFire operational data store


Start GemFire cluster with 1 locator and 3 servers

```
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

Create Region

```shell
create region --name=Account --type=PARTITION_REDUNDANT_PERSISTENT  --startup-recovery-delay=1000
```

Start Account Service

```shell
java -jar runtime/apps/account-service-1.0.0.jar --server.port=6001 --spring.data.gemfire.pool.locators="localhost[10334]"
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

Get Data 

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```

In gfsh

```shell
stop server --name=server1
```

Verify Server stopped is gfsh

```gfsh
list members
```

Verify no data lost

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```


Stop Server 2 in Gfsh

```gfsh
stop server --name=server2 
```

Verify 1 Servers

```gfsh
list members
```

Verify no Data Lost

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```


Restart Server 1

```shell
podman run -d -e 'ACCEPT_TERMS=y' --rm --name gf-server1 --network=gemfire -p 40404:40404 -p 7080:7080 -p 7977:7977 gemfire/gemfire-all:10.2-jdk21 gfsh start server --name=server1 --locators=gf-locator\[10334\] --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-port=7080 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7977  --J=-Duser.timezone=America/New_York --J=-Dgemfire.prometheus.metrics.interval=15s
```

Restart Server 2

```shell
podman run -d -e 'ACCEPT_TERMS=y' --rm --name gf-server2 --network=gemfire -p 1880:1880 -p 7778:7778 -p 7082:7082 gemfire/gemfire-all:10.2-jdk21 gfsh start server --name=server2 --locators=gf-locator\[10334\] --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-port=7082 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7778 --server-port=1880 --J=-Duser.timezone=America/New_York --J=-Dgemfire.prometheus.metrics.interval=15s
```

All Servers in Ready State

```gfsh
list members
```


Verify no Data Lost

```shell
curl -X 'GET' \
  'http://localhost:6001/accounts/1' \
  -H 'accept: */*';echo
```


# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```