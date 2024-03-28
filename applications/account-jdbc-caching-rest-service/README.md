# account-jdbc-caching-rest-service

The application is a [Postgres](https://www.postgresql.org/) + [Spring Caching](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching) Lookup Aside Cache + [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) example


# Demo Instructions

Under construction

## Docker building image

The following are the steps to build a docker image
```shell
mvn install
cd applications/account-jdbc-caching-rest-service
mvn spring-boot:build-image
```

Example for tagging and pushing to docker hub
```shell
docker tag account-jdbc-caching-rest-service:0.0.1-SNAPSHOT cloudnativedata/account-jdbc-caching-rest-service:0.0.1-SNAPSHOT
docker push cloudnativedata/account-jdbc-caching-rest-service:0.0.1-SNAPSHOT
```

