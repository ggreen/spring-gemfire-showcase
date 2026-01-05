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

SCDF  stream and re-create with the following


```scdf
vector-stream=http --port=7888| gemfire-vector-sink
```

Start Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.1-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="llama3.2:latest" --vector.service.url="http://localhost:7888"
```

Open Question HTML

```shell
open http://localhost:8088/ai.html
```

```shell
open http://localhost:8088/answer.html
```


*****************************
# Start DEMO

Ask Question

```text
Was Tanzu GemFire 10 dedicated to anyone?
```

Answer Question

- https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/

- Review logs in Data Flow


Ask Question

- Was Tanzu GemFire 10 dedicated to anyone?


---------------


Shutdown


```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "shutdown --include-locators"
```

Kill web application