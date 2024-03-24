# Spring GemFire Showcase

The project contains various examples using the In-Memory [NO-SQL](https://en.wikipedia.org/wiki/NoSQL) data management solution [GemFire](https://gemfire.dev/)


## OLTP GemFire Support

Real-time transactions can be fast and furious. Just think about building the next big retail market app.
Users of the app need to get the answers as quickly as possible. Any slow down to response times will have those users going somewhere else.
When the application is popular more and more users will come. Having highly scalable and fast data services is essential.
This session will highlight and describe

[What is OLTP?](https://www.oracle.com/database/what-is-oltp/)
What are its characteristics?
What are the data service challenges?

[GemFire can be used to meet the OLTP](https://www.youtube.com/watch?v=oy_Yq_mf45Y) needs such as:

- Performance
- Scalability
- Strong Consistency
- NoSQL characteristics
- High Availability
- Fault Tolerance
- WAN Replication


## Applications


| Application                                                                         | Notes                                       | 
|-------------------------------------------------------------------------------------|---------------------------------------------|
| [account-service](applications/account-service)                                     | Sprint Boot for GemFire client application  |
| [account-location-event-service](applications/account-location-event-service)       | Sprint Boot Continuous Query example        |
| [account-jdbc-caching-rest-service](applications/account-jdbc-caching-rest-service) | Sprint Boot JDBC Lookup Aside Cache example |



## Build Jar

Set Pivotal Maven Repository user credentials
See https://gemfire.dev/quickstart/java/

```shell
export PIVOTAL_MAVEN_USERNAME=$HARBOR_USER
export PIVOTAL_MAVEN_PASSWORD=$HARBOR_PASSWORD
```

# Starting a GemFire Cluster Locally


In GFfsh

Start Locator
```shell
start locator --name=localhost --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
```

Start Server
```shell
start server --name=server1 --server-bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --jmx-manager-hostname-for-clients=127.0.0.1 --bind-address=127.0.0.1 --http-service-bind-address=127.0.0.1 --locators=127.0.0.1[10334]
```
