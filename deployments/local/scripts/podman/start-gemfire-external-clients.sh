
podman network create gemfire --driver bridge


# Run Locator
podman run -d -e 'ACCEPT_TERMS=y' --rm --name gf-locator --network=gemfire -p 10334:10334 -p 1099:1099 -p 7070:7070 -p 7777:7777 gemfire/gemfire-all:10.1 gfsh start locator --name=locator1 --jmx-manager-hostname-for-clients=127.0.0.1 --hostname-for-clients=127.0.0.1 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7777  --J=-Duser.timezone=America/New_York --J=-Dgemfire.prometheus.metrics.interval=15s

until podman exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" >/dev/null 2>&1; do
  echo "Waiting for locator to start..."
  sleep 2
done
echo "Locator is up"


# Configure PDX
podman exec -it gf-locator gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "configure pdx --read-serialized=true --disk-store"
# Run Cache Server
#podman run -d  -e 'ACCEPT_TERMS=y' --rm --name gf-server1 --network=gemfire -p 40404:40404 gemfire/gemfire-all:10.1 gfsh start server --name=server1 --locators=gf-locator\[10334\]
podman run -d -e 'ACCEPT_TERMS=y' --rm --name gf-server1 --network=gemfire -p 40404:40404 -p 7080:7080 -p 7977:7977 gemfire/gemfire-all:10.1 gfsh start server --name=server1 --locators=gf-locator\[10334\] --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-port=7080 --J=-Dgemfire.prometheus.metrics.emission=Default --J=-Dgemfire.prometheus.metrics.port=7977  --J=-Duser.timezone=America/New_York --J=-Dgemfire.prometheus.metrics.interval=15s

# Setup GemFire Account Region
#podman exec -it gf-locator gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=Account --type=PARTITION  --enable-statistics=true"

# Setup GemFire Location Region
#podman exec -it gf-locator gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=Location --type=PARTITION --enable-statistics=true"


