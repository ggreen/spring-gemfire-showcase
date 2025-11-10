Start GemFire

```shell
deployments/local/scripts/podman/gemfire-for-ai.sh
```


Start Ollama

```shell
ollama serve
```

Start SCDF

```shell
echo processor.remove-by-gf-search=file://$PWD/applications/processor/remove-by-gf-search-processor/target/remove-by-gf-search-processor-0.0.1-SNAPSHOT.jar
echo processor.remove-by-gf-search.bootVersion=3
echo sink.gemfire-vector-sink=file://$PWD/applications/sink/gemfire-vector-sink/target/gemfire-vector-sink-0.0.1-SNAPSHOT.jar
echo sink.gemfire-vector-sink.bootVersion=3
```

START RABBITMQ!!!!!

```shell
podman run -it --rm --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.2-management

```

First stream

```scdf
vector-stream=http --port=7888| gemfire-vector-sink
```

Start Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.1-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="llama3" --vector.service.url="http://localhost:7888"
```

Open Question HTML

```shell
open http://localhost:8088/ai.html
```

Open Answer HTML

```shell
open http://localhost:8088/answer.html
```


****************************


Ask Question
- What is the first application development use case for GemFire?
- What is the first application development use case for Tanzu GemFire?


```shell
open http://localhost:8088/answer.html
```

Answer
- The first use case for GemFire was mainframe application modernization for low latency data access

View Data Flow Logs

Question
- What is the first application development use case for Tanzu GemFire?


AI UI
- Clean Answer using the Trash icon
- What is the first application development use case for Tanzu GemFire?
- What is the first application development use case for Tanzu GemFire? (cached - faster response)
- What is the first application development use case for GemFire?


SCDF Destroy stream and re-create with the following


```scdf
vector-stream=http --port=7888 | remove-by-gf-search --gemfire.remove.search.indexName=SearchResultsIndex --gemfire.remove.search.regionName=SearchResults --gemfire.remove.search.defaultField=__REGION_VALUE_FIELD | gemfire-vector-sink
```


Answer Question

- The first application development use case for GemFire was real-time financial trading applications
- View Logs in Data Flow


Ask Question
- What is the first application development use case for GemFire?
- What is the first application development use case for GemFire for Tanzu GemFire?


Ask Question

- Was VMware GemFire 10 release was dedicated to anyone?

Answer Question

- https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/
- Review logs in Data Flow

Ask Question

- What was Tanzu GemFire 10 dedicated to anyone?




---------------


Shutdown


```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "shutdown --include-locators"
```

```shell
$GEMFIRE_HOME/bin/gfsh -e connect -e "search lucene --name=SearchResultsIndex --region=SearchResults --queryString='GemFire 10' --defaultField=__REGION_VALUE_FIELD --limit=10"
```

qwen3

Use wizardlm2:7b

-----------

```shell
http://localhost:7080/gemfire-vectordb/swagger-ui/index.html
```