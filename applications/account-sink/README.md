# account-sink

A simple developer defined [spring cloud stream](https://spring.io/projects/spring-cloud-stream) sink app to save domain data to a domain object.
This app can be added to a library of apps to be used within [SCDF](https://spring.io/projects/spring-cloud-dataflow). 

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

------------------------
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
- Click import application coordinates from a properties file

Paste the following with the locations of GemFire SCDF and custom apps

Note: Update location based on your local machine

```properties
sink.account-sink=file:///Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/applications/account-sink/target/account-sink-0.0.1-SNAPSHOT.jar
```

Note: please update the URI based on built the custom apps and location of the downloaded GemFire for Spring Cloud DataFlow applications

--------------------------------
# Http to GemFire

- Click [Streams](http://localhost:9393/dashboard/index.html#/streams/list) ->  [Create](http://localhost:9393/dashboard/index.html#/streams/list/create)
- Paste the following example


Access the Spring DataFlow Dashboard

```shell
open http://localhost:9393/dashboard
```


Create Stream the following definition

```shell
accounts=http --port=8802 | account-sink --spring.data.gemfire.pool.default.locators="localhost[10334]"
```

Deploy Stream



````shell
curl -X 'POST' \
'http://localhost:8802' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "@type" : "spring.gemfire.showcase.account.domain.account.Account",
"id": "70",
"name": "Account 70"
}';echo
````


````shell
curl -X 'POST' \
'http://localhost:8802' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "@type" : "spring.gemfire.showcase.account.domain.account.Account",
"__GEMFIRE_JSON" : "spring.gemfire.showcase.account.domain.account.Account",
"id": "71",
"name": "Account 71"
}';echo
````

