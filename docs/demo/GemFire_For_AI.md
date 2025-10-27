Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.model="llama3"
```

```shell
open http://localhost:8088/question.html
```

```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "create region --name=SearchResults --type=PARTITION"
```


Create Index

```shell
$GEMFIRE_HOME/bin/gfsh -e connect -e "create lucene index --name=SearchResultsIndex --region=/SearchResults  --field=__REGION_VALUE_FIELD"
```

Create region

```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "create region --name=SearchResults --type=PARTITION"
```



--------------------------------------------------------


```shell
$GEMFIRE_HOME/bin/gfsh -e connect -e "search lucene --name=SearchResultsIndex --region=SearchResults --queryString='What does HealthMap Solutions do' --defaultField=__REGION_VALUE_FIELD --limit=10"
```



Start Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="llama3"
```

What is the capital of NJ

Add Context
- The capital of NJ is Trenton



What are the top methods to treat liver disease


qwen3

Use wizardlm2:7b

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="llama3" --vector.service.url="http://localhost:7888"
```

```shell
open http://localhost:8083/question.html
```


Adding

Tell me about Healthmap solutions

```shell
curl -X 'POST' \
'http://localhost:7088/functions/saveToVectorStore' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"urls": [
"https://healthmapsolutions.com/about-us/"
]
}'
```


Start SCDF


```shell
echo processor.remove-by-gf-search=file://$PWD/applications/processor/remove-by-gf-search-processor/target/remove-by-gf-search-processor-0.0.1-SNAPSHOT.jar
echo processor.remove-by-gf-search.bootVersion=3
echo sink.gemfire-vector-sink=file://$PWD/applications/sink/gemfire-vector-sink/target/gemfire-vector-sink-0.0.1-SNAPSHOT.jar
echo sink.gemfire-vector-sink.bootVersion=3
```


```scdf
vector-stream=http --port=7888 | remove-by-gf-search --gemfire.remove.search.indexName=SearchResultsIndex --gemfire.remove.search.regionName=SearchResults --gemfire.remove.search.defaultField=__REGION_VALUE_FIELD | gemfire-vector-sink
```