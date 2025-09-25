
START ollama in Podman

```shell
podman run -it --rm -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
```

Start GemFire Cluster

```shell
./deployments/local/scripts/podman/start-gemfire.sh
```