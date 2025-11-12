# GemFire fo AI Demo

## Pre-requisite


Start SCDF

```shell
echo processor.remove-by-gf-search=file://$PWD/applications/processor/remove-by-gf-search-processor/target/remove-by-gf-search-processor-0.0.1-SNAPSHOT.jar
echo processor.remove-by-gf-search.bootVersion=3
echo sink.gemfire-vector-sink=file://$PWD/applications/sink/gemfire-vector-sink/target/gemfire-vector-sink-0.0.1-SNAPSHOT.jar
echo sink.gemfire-vector-sink.bootVersion=3
```

START SCDF

START RABBITMQ!!!!!

```shell
podman run -it --rm --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.2-management
```


Start Ollama

```shell
ollama serve
```

-----------------------------
Start GemFire

```shell
deployments/local/scripts/podman/gemfire-for-ai.sh
```


Start GemFire MCP Server

```shell
java -jar applications/web/ai-mcp-server-app/target/ai-mcp-server-app-0.0.1-SNAPSHOT.jar
```

Open SCDF
```text
open http://localhost:9393/dashboard/index.html#/streams/list
```

Create First stream

```scdf
vector-stream=http --port=7888| gemfire-vector-sink
```


Start Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.1-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="llama3.1:8b" --vector.service.url="http://localhost:7888"
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
- What is the first application development use case for GemFire? DO NOT USE ANY TOOLS
- What is the first application development use case for Tanzu GemFire? DO NOT USE ANY TOOLS


```shell
open http://localhost:8088/answer.html
```

Answer
- The first use case for GemFire was mainframe application modernization for low latency data access

View Data Flow Logs

Question
- What is the first application development use case for Tanzu GemFire? DO NOT USE ANY TOOLS


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
- What is the first application development use case for GemFire? DO NOT USE ANY TOOLS
- What is the first application development use case for GemFire for Tanzu GemFire? DO NOT USE ANY TOOLS


Ask Question

- Was VMware GemFire 10 release was dedicated to anyone? DO NOT USE ANY TOOLS

Answer Question

- https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/
- Review logs in Data Flow

Ask Question

- What was Tanzu GemFire 10 dedicated to anyone? DO NOT USE ANY TOOLS


---------------
# MCP Example

Prompt

```prompt
Save the value for a given key where key=1 value=Hello World
```


```prompt
Finds the value for a specific cached key where key=1
```

```text
Save the value for a given key where key=arul value="Arul is an expert in Messaging and Database solutions. He may miss the current Data TSL call because he is working on big RabbitMQ deal a major enterprise technology company;. He is one of the nicest people you will ever find."
```

```prompt
Finds the value for a specific cached key where key=arul
Tell me why Arul is not on the Data TSL call.
```

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