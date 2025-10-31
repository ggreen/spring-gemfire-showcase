Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


Create Index

```shell
$GEMFIRE_HOME/bin/gfsh -e connect -e "create lucene index --name=SearchResultsIndex --region=/SearchResults  --field=__REGION_VALUE_FIELD"
```

Create region

```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "create region --name=SearchResults --type=PARTITION"
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

Ask Question
- What is the capital of NJ
- What is the capital of New Jersey


```shell
open http://localhost:8088/answer.html
```


Answer Question
- The capital of NJ is Trent

Ask Question
- What is the capital of New Jersey

View Data Flow Logs

AI UI
- Clean Answer using the Trash icon
- What is the capital of New Jersey
- What is the capital of New Jersey (cached - faster response)


Ask Quqstion
- What is the capital of NJ


SCDF Destroy stream and re-create with the following


```scdf
vector-stream=http --port=7888 | remove-by-gf-search --gemfire.remove.search.indexName=SearchResultsIndex --gemfire.remove.search.regionName=SearchResults --gemfire.remove.search.defaultField=__REGION_VALUE_FIELD | gemfire-vector-sink
```


Answer Question

- The capital of NJ is Trenton
- View Logs in Data Flow


Ask Question
- What is the capital of NJ
- What is the capital of NJ
- What is the capital of New Jersey
- Does Healthmap specializes in Kidney Population Health Management


Answer Question

- https://healthmapsolutions.com/about-us/
- Review logs in Data Flow

Ask Question

- Does Healthmap specializes in Kidney Population Health Management
- Does Healthmap specializes in Kidney Population Health Management


---------------


Shutdown


```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "shutdown --include-locators"
```

```shell
$GEMFIRE_HOME/bin/gfsh -e connect -e "search lucene --name=SearchResultsIndex --region=SearchResults --queryString='What does HealthMap Solutions do' --defaultField=__REGION_VALUE_FIELD --limit=10"
```

qwen3

Use wizardlm2:7b