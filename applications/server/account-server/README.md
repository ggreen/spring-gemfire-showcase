# account-server

This repository provides an example of a Peer-To-Peep GemFire cluster 
that using Spring Data GemFire.


Prerequisite

- GemFire 10.x
- Java 17



# Getting Started Cluster

Start locator

```shell
mkdir -p runtime/locator
cd runtime/locator
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED -jar ../../applications/server/locator/target/locator-0.0.1-SNAPSHOT.jar  --spring.data.gemfire.pool.locators="localhost[10334]" --gemfire.locator.name=account-locator-1  --gemfire.locator.port=10334
```

Start Server 1
```shell
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED -jar applications/server/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --spring.data.gemfire.pool.locators="localhost[10334]" --gemfire.server.name=account-srv1  --gemfire.server.port=40001
```

Start Server 2

```shell
mkdir -p cd runtime/acct2
cd runtime/acct2
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED  -jar ../../applications/server/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --gemfire.server.name=account-srv2 --spring.data.gemfire.pool.locators="localhost[10334]" --server.port=6012 --gemfire.server.port=40002 --membership-port-range=61001-61101
```





--add-exports java.base/java.management=ALL-UNNAMED

---------------


--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.management/com.sun.jmx.remote.security=ALL-UNNAMED