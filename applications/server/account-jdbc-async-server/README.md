# account-server


This repository provides an example of a Peer-To-Peep GemFire cluster 
that using Spring Data GemFire.


Prerequisite

- GemFire 10.x
- Java 17



# Getting Started

Start Server with embedded locator


```shell
java -jar applications/account-server/target/account-server-0.0.1-SNAPSHOT.jar  --startLocator=true --gemfire.server.name=account-srv1 
```





--add-opens java.base/java.management=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.management/com.sun.jmx.remote.security=ALL-UNNAMED

--add-exports java.base/java.management=ALL-UNNAMED

---------------


--add-opens java.base/java.util=ALL-UNNAMED --add-opens java.management/com.sun.jmx.remote.security=ALL-UNNAMED