# Spring GemFire Showcase

The project contains various examples using the In-Memory [NO-SQL](https://en.wikipedia.org/wiki/NoSQL) data management solution [GemFire](https://gemfire.dev/)


This [spring-gemfire-showcase](https://github.com/ggreen/spring-gemfire-showcase) repository has [Spring](https://spring.io/) + [GemFire](https://tanzu.vmware.com/gemfire) examples with [Docker](https://www.docker.com/products/docker-desktop/)-based demo instructions see . This information can be shared with the customer.

It currently showcases

- Running [GemFire clusters](https://gemfire.dev/tutorials/java/gemfire_basics/), [GemFire Management Console](https://docs.vmware.com/en/VMware-GemFire-Management-Console/index.html), and [Spring Boot](https://spring.io/projects/spring-boot) applications in Docker
- [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) Create Read Update Delete [(CRUD) repositories](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html)
- [Spring Batch](https://spring.io/projects/spring-batch) with GemFire - moving data from a database like Postgres into GemFire
- [GemFire Continuous Query](https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-continuous_querying-how_continuous_querying_works.html) with Spring Data for GemFire
- [Spring Caching](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching) with Spring Data for GemFire - caching data from Postgres with Spring Web Rest APIs


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


| Project                                                                               | Notes                                                                                                                                                                                                                                                                      |
|---------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [account-batch](applications%2Faccount-batch)                                         | [Spring Batch](https://spring.io/batch) + [GemFire](https://gemfire.dev/) example.                                                                                                                                                                                         |
| [account-jdbc-caching-rest-service](applications%2Faccount-jdbc-caching-rest-service) | [Postgres](https://www.postgresql.org/) + [Spring Caching](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching) Lookup Aside Cache + [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) example |
| [account-location-event-service](applications%2Faccount-location-event-service)       | [Continuous Query](https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-continuous_querying-how_continuous_querying_works.html) [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) example                            |
| [account-service](applications%2Faccount-service)                                     | [Spring Web](https://spring.io/web-applications) + [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) example                                                                                                                 |

## Build Jar

Set Pivotal Maven Repository user credentials
See https://gemfire.dev/quickstart/java/

```shell
export PIVOTAL_MAVEN_USERNAME=$HARBOR_USER
export PIVOTAL_MAVEN_PASSWORD=$HARBOR_PASSWORD
```
