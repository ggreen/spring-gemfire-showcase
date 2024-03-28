# account-location-event-service

The application is a [Continuous Query](https://docs.vmware.com/en/VMware-GemFire/10.1/gf/developing-continuous_querying-how_continuous_querying_works.html) [Spring Data for GemFire](https://docs.vmware.com/en/Spring-Data-for-VMware-GemFire/index.html) example

# Demo Instructions

Run Script to run GemFire in Docker

If Not already running

```shell
./deployments/local/scripts/docker/start-docker-gemfire.sh
```

Run Service in Docker

```shell
docker run --rm -it --name account-location-event-service -e "server.port=6001" -e "spring.data.gemfire.pool.default.locators=gf-locator[10334]" --network=gemfire-cache cloudnativedata/account-location-event-service:0.0.1-SNAPSHOT
```

Use **applications/account-service** to Post data with the following PayLoad

```json
{
  "id": "VMware1",
  "name": "VMware"
}
```

View data Management Console

If not started see

```shell
./deployments/local/scripts/docker/start-gmc-gideon-console.sh
```

```shell
open http://localhost:8080/
```

## Docker building image

The following are the steps to build a docker image
```shell
mvn install
cd applications/account-location-event-service
mvn spring-boot:build-image
```

Example for tagging and pushing to docker hub
```shell
docker tag account-location-event-service:0.0.1-SNAPSHOT cloudnativedata/account-location-event-service:0.0.1-SNAPSHOT
docker push cloudnativedata/account-location-event-service:0.0.1-SNAPSHOT
```

