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

# Projects

| Project                                                                               | Notes                                                                      |
|---------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| [account-batch](applications%2Faccount-batch)                                         | Spring Batch + GemFire example                                             |
| [account-jdbc-caching-rest-service](applications%2Faccount-jdbc-caching-rest-service) | Postgres + Spring Cache + Lookup Aside Cache + Spring Data GemFire example |
| [account-location-event-service](applications%2Faccount-location-event-service)       | Continous Query Spring Data GemFire example                                |
| [account-service](applications%2Faccount-service)                                     | Spring Web + Spring Data GemFire example                                   |