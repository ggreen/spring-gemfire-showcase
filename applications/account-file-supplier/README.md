# account-file-supplier

A simple developer defined supplier to publish lines in a file to RabbitMQ.

| Configuration  Properties  | Notes                                                 | Default Value |
|----------------------------|-------------------------------------------------------|---------------|
| file.directory             | The file directory to look for text files             |               |
| file.modification.delay.ms | The delay duration if the file is being changed       |               |
| spring.application.name    | The application name used as in the Rabbit connection |               |
| spring.rabbitmq.username   | The rabbitmq username                                 | guest         |
| spring.rabbitmq.password   | The rabbitmq guest                                    | guest         |
| spring.rabbitmq.host       | The rabbitmq broker hostname                          | 127.0.0.1     |



# SCDF Application Register

The following is an example properties to register this application as SCDF source.
Change the directory path to match your local environment.

```properties
source.account-file=file:///Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/applications/account-file-supplier/target/account-file-supplier-0.0.1-SNAPSHOT.jar
```

