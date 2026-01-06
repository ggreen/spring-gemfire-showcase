deployments/local/scripts/podman/start-gemfire-external-clients.sh

sleep 1
# Waiting Server
until podman exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "status server --name=server1" >/dev/null 2>&1; do
  echo "Waiting for server to start..."
  sleep 2
done
echo "Server is up"

podman exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create lucene index --name=SearchResultsIndex --region=/SearchResults  --field=__REGION_VALUE_FIELD"
podman exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=SearchResults --type=PARTITION"
podman exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=LookUp --type=PARTITION"
