#!/bin/bash
# container-runtime.sh - Container runtime detection and compatibility layer
#
# This script provides compatibility between podman and docker by:
# 1. Detecting which container runtime is available
# 2. Setting the CR (Container Runtime) variable for use in scripts
# 3. Creating a 'podman' function that wraps docker (if docker is being used)
#    so that direct 'podman' commands in documentation work with docker
#
# Usage in scripts:
#   source "$(dirname "$0")/../container-runtime.sh"
#   $CR run -it --rm alpine echo "Hello"
#
# Usage for workshop labs (run once per terminal session):
#   source deployments/local/scripts/container-runtime.sh
#   # Now 'podman' commands will work even if you only have docker

# Detect container runtime
if command -v podman &> /dev/null; then
    export CR=podman
    echo "Container runtime: podman"
elif command -v docker &> /dev/null; then
    export CR=docker
    echo "Container runtime: docker"

    # Create a 'podman' function that calls docker
    # This allows copy-paste of podman commands to work with docker
    podman() {
        docker "$@"
    }
    export -f podman
else
    echo "Error: Neither podman nor docker found in PATH" >&2
    echo "Please install podman or docker to continue." >&2
    # Don't exit if being sourced - just warn
    if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
        exit 1
    fi
fi
