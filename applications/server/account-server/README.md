# account-server

This projects provides an example of a [Spring Boot](https://spring.io/projects/spring-boot) Peer-To-Peer GemFire server 
that using Spring Data GemFire.


Prerequisite

- GemFire 10.x
- Java 17


# Getting Started Cluster

Start Server 1

```shell
mkdir -p runtime/server1
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED -jar applications/server/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --spring.data.gemfire.pool.locators="localhost[10334]" --gemfire.working.dir=runtime/server1 --gemfire.server.name=account-srv1  --gemfire.server.port=40001
```