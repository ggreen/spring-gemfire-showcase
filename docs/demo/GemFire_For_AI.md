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

Create region

```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "create region --name=SearchResults --type=PARTITION"
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
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8083 --spring.ai.ollama.chat.options.model="wizardlm2:7b"
```

```shell
open http://localhost:8083/question.html
```


Adding

Tell me about Healthmap solutions

curl -X 'POST' \
'http://localhost:7088/functions/saveToVectorStore' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"urls": [
"https://healthmapsolutions.com/about-us/"
]
}'
