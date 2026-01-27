# Local Near Cache Pattern

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
java -jar  runtime/apps/account-location-service-1.0.0.jar
```


```java
 @Bean("Location")
ClientRegionFactoryBean<String, Location> location(ClientCache gemfireCache)
{
    Interest[] interests = {new Interest(Interest.ALL_KEYS)};
    var bean= new ClientRegionFactoryBean<String,Location>();
    bean.setCache(gemfireCache);
    bean.setDataPolicy(DataPolicy.NORMAL);
    bean.setInterests(interests);
    bean.setName("Location");
    return bean;
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
curl -w "\n Total Time:    %{time_total}s\n"  -X 'GET' \
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

```gfsh
shutdown
list members
```

Get data with not servers

```shell
curl -X 'GET' \
  'http://localhost:8081/locations/location/1' \
  -H 'accept: */*';echo
```


In gfsh


Start Server Back

```shell
podman run -d -e 'ACCEPT_TERMS=y' --rm --name gf-server1 --network=gemfire -p 40404:40404 -p 7080:7080 -p 7977:7977 gemfire/gemfire-all:10.2-jdk21 gfsh start server --name=server1 --locators=gf-locator\[10334\] --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-port=7080 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7977  --J=-Duser.timezone=America/New_York --J=-Dgemfire.prometheus.metrics.interval=15s
```

In gfsh Verify server ready

```gfsh
list members
```

Get Data

```shell
curl -X 'GET' \
  'http://localhost:8081/locations/location/1' \
  -H 'accept: */*';echo
```

Empty (local updated)


------------------------------

# Cleanup

Stop all applications

Shutdown GemFire

```shell
podman exec -it gf-locator gfsh -e "connect" -e "shutdown --include-locators"
```
