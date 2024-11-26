# account-server


This repository provides an example of a Peer-To-Peep GemFire cluster 
that using Spring Data GemFire.


Prerequisite

- GemFire 10.x
- Java 17



# Getting Started

Start Server with embedded locator

-Dgemfire.membership-port-range=10000-10010

Start Server 1 and Locator


--startLocator="localhost[10334]"
```shell
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED -jar applications/server/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --spring.data.gemfire.pool.locators="localhost[10334]" --gemfire.server.name=account-srv1  --gemfire.server.port=40001
```

--gemfire.start-locator=10335
-Dgemfire.start-locator="localhost[10335]"

```shell
cd runtime/acct2
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED  -jar ../../applications/server/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --gemfire.server.name=account-srv2 --spring.data.gemfire.pool.locators="localhost[10334]" --server.port=6012 --gemfire.server.port=40002 --membership-port-range=61001-61101
```





--add-exports java.base/java.management=ALL-UNNAMED

---------------


--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.management/com.sun.jmx.remote.security=ALL-UNNAMED