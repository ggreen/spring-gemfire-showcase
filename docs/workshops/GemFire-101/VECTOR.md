

Put document source - source gemfire cluster

Sink to Vector GemFire cluster



Start Rabbitmq

```shell
podman network create gemfire
```

```shell
podman run --network gemfire --rm -it --name rabbitmq4 \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:4-management
```


Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```
Create Region


```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=DocumentSource --type=PARTITION"
```


```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=SearchResults --type=PARTITION"
```



--------------

Download SCDF Jars (optional first time only)
- SCDF Server
- Skipper
- Shell

```shell
mkdir -p runtime/scdf
wget  --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.5/spring-cloud-dataflow-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-skipper-server/2.11.5/spring-cloud-skipper-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.11.5/spring-cloud-dataflow-shell-2.11.5.jar
```

Start Skipper
```shell
export ROOT_DIR=$PWD
java -jar runtime/scdf/spring-cloud-skipper-server-2.11.5.jar
```


Start Data Flow Server
```shell
export ROOT_DIR=$PWD 
export SPRING_APPLICATION_JSON='{"spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.username":"guest","spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.password":"guest","spring.rabbitmq.username":"user","spring.rabbitmq.password":"bitnami","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.username" :"guest","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.password" :"guest"}'

java -jar runtime/scdf/spring-cloud-dataflow-server-2.11.5.jar
```


Register Source/Sink

```open
http://localhost:9393/dashboard/index.html#/apps
```


Generate Register Script

```shell
mkdir -p runtime/scripts
echo app register --name postgres --type sink --bootVersion 3 --uri file://$PWD/applications/sinks/postgres-sink/target/postgres-sink-0.0.1-SNAPSHOT.jar --metadataUri file://$PWD/applications/sinks/postgres-sink/target/postgres-sink-0.0.1-SNAPSHOT-metadata.jar > runtime/scripts/postgres-sink-register.shell
cat runtime/scripts/postgres-sink-register.shell
```


Register Sink

```shell
java -jar runtime/scdf/spring-cloud-dataflow-shell-2.11.5.jar --dataflow.uri=http://localhost:9393 --spring.shell.commandFile=runtime/scripts/postgres-sink-register.shell
````



SCDF

```shell
document-source=gemfire: geode --region-name=DocumentSource --pdx-read-serialized=true --host-addresses=gf-locator:10334 --subscription-enabled=true --connect-type=locator --event-expression=newValue | log
```


```shell
document-source=gemfire: geode --region-name=DocumentSource --pdx-read-serialized=true --host-addresses=gf-locator:10334 --subscription-enabled=true --connect-type=locator --event-expression=newValue | gemfire-vector-sink --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080
```

Start Web Applications

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088
```


Question: what is the capital of NJ

Add document source

```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "put --region=DocumentSource --key=njCapital --value='{\"content\" : \"The capital of NJ is Trenton.\"}'"
```

Question: what is the capital of NJ

```shell
curl -X 'POST' \
  'http://localhost:8088/vector/search' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d 'what is the capital of NJ'
```

Add document source 


```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "put --region=DocumentSource --key=1 --value='{\"content\" : \"This is a sample document content.\",\"urls\" : [ \"https://healthmapsolutions.com/about-us/\",\"https://github.com/ggreen?fg_force_rendering_mode=Images&fireglass_rsn=true#fireglass_params&tabid=a2c8ae713fb7ae4e&start_with_session_counter=2&application_server_address=isolation-3-us-east4.prod.fire.glass\"]}'"
```


Gfsh 

```shell
query --query="select * from /SearchResults.entries"
```