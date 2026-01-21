# account-paging


Start GemFire

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```

Launch gfsh (Ex: in Podman)

```shell
podman exec -it gf-locator gfsh
```

Connection to locator

```gfsh
connect
```

Create Account Region

```shell
create region --name=Account --type=PARTITION
```

Start Account Paging App