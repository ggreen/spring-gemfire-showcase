#!/bin/bash

# Source container runtime detection (supports both podman and docker)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/../container-runtime.sh"

# Start GemFire cluster
"$SCRIPT_DIR/start-gemfire-external-clients.sh"

sleep 1
# Waiting Server
until $CR exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "status server --name=server1" >/dev/null 2>&1; do
  echo "Waiting for server to start..."
  sleep 2
done
echo "Server is up"

$CR exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create lucene index --name=SearchResultsIndex --region=/SearchResults  --field=__REGION_VALUE_FIELD"
$CR exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=SearchResults --type=PARTITION"
$CR exec -it  gf-locator  gfsh -e "connect --jmx-manager=gf-locator[1099]" -e "create region --name=LookUp --type=PARTITION"
