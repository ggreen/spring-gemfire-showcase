
START ollama in Podman

```shell
podman run -it --rm -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```


Start RabbitMQ

```shell
podman run -it --rm --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:4-management
```



Start GemFire Cluster

```shell
./deployments/local/scripts/podman/start-gemfire.sh
```


Services exposed
```shell
Locator-port=10334
Server-port=40405
Management-console=http://localhost:7072/
```


To add the running cluster
click “Connect”
host: gemfire-locator-0
port: 7080
Data API: http://localhost:7080/gemfire-api/
Vector Database API: http://localhost:7080/gemfire-vectordb


```json
{
  "content": "Hello world"
}
```