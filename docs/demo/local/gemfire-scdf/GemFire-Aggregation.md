# SCDF for GemFire

-----------------------

# Local Testing

## Start GemFire

```shell
./deployments/local/scripts/gemfire/start.sh
```

[Setup and Install SCDF](https://dataflow.spring.io/docs/installation/local/manual/)

------------------------
## Start RabbitMQ

See [RabbitMQ documentation](https://www.rabbitmq.com/docs/download)

##  Start SCDF

Start Skipper
```shell
java -jar $SCDF_HOME/spring-cloud-skipper-server-2.11.2.jar  
```

Start DataFlow
```shell
java -jar $SCDF_HOME/spring-cloud-dataflow-server-2.11.2.jar --server.port=9393  --spring.cloud.dataflow.features.skipper-enabled=true 
```

Access the Spring DataFlow Dashboard

```shell
open http://localhost:9393/dashboard
```

Goto Click [Apps](http://localhost:9393/dashboard/index.html#/apps) ->[Add](http://localhost:9393/dashboard/index.html#/apps/add)

- Click import application starters -> RabbitMQ/Maven -> Import Applications
- Click App as Properties

Paste the following with the locations of GemFire SCDF and custom apps


```properties
source.gemfire=file:///Users/devtools/repositories/IMDG/gemfire/scdf/apps/gemfire-source-rabbit-1.0.1.jar
source.gemfire.metadata=file:///Users/devtools/repositories/IMDG/gemfire/scdf/apps/gemfire-source-rabbit-1.0.1-metadata.jar
sink.gemfire=file:///Users/devtools/repositories/IMDG/gemfire/scdf/apps/gemfire-sink-rabbit-1.0.1.jar
sink.gemfire.metadata=file:///Users/devtools/repositories/IMDG/gemfire/scdf/apps/gemfire-sink-rabbit-1.0.1-metadata.jar
```

Note: please update the URI based on built the custom apps and location of the downloaded GemFire for Spring Cloud DataFlow applications

--------------------------------
Deploy the following SCDF definition


```shell
gemfire-aggregation-log=gemfire --connect-type=locator --host-addresses=localhost:10334 --gemfire.region.regionName=Account - -pdx-read-serialized=true | aggregator --aggregator.message-store-type=jdbc --spring.datasource.url=jdbc:h2:mem:test --spring.datasource.schema=classpath://org/springframework/integration/jdbc/schema-h2.sql | log --level=INFO
```
