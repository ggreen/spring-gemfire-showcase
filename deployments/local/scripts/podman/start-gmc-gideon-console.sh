#!/bin/bash

# Source container runtime detection (supports both podman and docker)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/../container-runtime.sh"

$CR run -it -p 8080:8080 --rm --name gmc-console --network=gemfire gemfire/gemfire-management-console:1.4
