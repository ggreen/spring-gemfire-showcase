
Configuration for GemFire Vector Sink

| Configuration                      | Notes         | Example   |
|------------------------------------|---------------|-----------|
| spring.rabbitmq.username           | ex: guest     | guest     |
| spring.rabbitmq.password           | ex: guest     | guest     |
| spring.rabbitmq.host               | ex: localhost | localhost |
| spring.ai.vectorstore.gemfire.host | ex: localhost | localhost |
| spring.ai.vectorstore.gemfire.port | ex: 7080      | 7080      |


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
  "content" : "This is a sample document content.",
  "urls" : [ 
    "https://healthmapsolutions.com/about-us/",
    "https://github.com/ggreen?fg_force_rendering_mode=Images&fireglass_rsn=true#fireglass_params&tabid=a2c8ae713fb7ae4e&start_with_session_counter=2&application_server_address=isolation-3-us-east4.prod.fire.glass"
  ]
}
```