
```shell
deployments/local/scripts/podman/gemfire-for-ai.sh
```

```shell
ollama serve
```

```shell
java -jar applications/sink/gemfire-vector-sink/target/gemfire-vector-sink-0.0.1-SNAPSHOT.jar
```

```shell
open http://localhost:7088
```

```shell
curl -X 'POST' \
  'http://localhost:7088/functions/saveToVectorStore' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '"I love Spring AI"'
```