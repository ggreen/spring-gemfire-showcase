Configuration for GemFire Vector Sink

| Configuration                      | Notes                      | 
|------------------------------------|----------------------------|
| spring.ai.vectorstore.gemfire.host | ex: localhost              |
| spring.ai.vectorstore.gemfire.port | ex: 7080                   |
| spring.ai.ollama.base-url          | ex: http://localhost:11434 |
 | server.port                         | ex: 8088                   |



Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```

START ollama in Podman

```shell
podman run -it --rm -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

Start GemFire Cluster

```shell
./deployments/local/scripts/podman/start-gemfire.sh
```

Create region 

```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "create region --name=SearchResults --type=PARTITION"
```

 Build Web Application

```shell

Start Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.2-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088
```

Open Web Application

```shell
open http://localhost:8088 
```
