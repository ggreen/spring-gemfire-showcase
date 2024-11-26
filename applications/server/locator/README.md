# Locator


The project contains an example [Spring Boot](https://spring.io/projects/spring-boot) application with 
an embedded [GemFire Locator](https://docs.vmware.com/en/VMware-GemFire/10.1/gf/configuring-running-running_the_locator.html).


## Getting Started

From Project Root

```shell
mkdir -p runtime/locator
java  --add-exports java.management/sun.management=ALL-UNNAMED  --add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-exports=java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED -jar applications/server/locator/target/locator-0.0.1-SNAPSHOT.jar --gemfire.working.dir=runtime/locator  --spring.data.gemfire.pool.locators="localhost[10334]" --gemfire.locator.name=account-locator-1  --gemfire.locator.port=10334
```