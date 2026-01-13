# Spring Data for GemFire Workshop

This workshop demonstrates how to use Spring Data for VMware GemFire.

# Agenda 101

Overview

- Start Clustering
    - Gfsh
- App READ/WRITE
- Querying
- GemFire as a cache
- Proxy versus Cache Proxy
- GemFire functions
- GemFire operational data store

Download Apps

```shell
mkdir -p runtime/apps
wget -P runtime/apps  https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Spring-Workshop-v1/account-service-1.0.0.jar
```

| Lab   | Description                            |
|-------|----------------------------------------|
| 01    | [Cache Listener](01_Cache_Listener.md) |
| 02    | CQ Listener                            |
| 03    | Event Persistence                      |
| 04    | Async Event Queues                     |
| 05    | Tanzu Data Flow - GF Events (BONUS)    |
|
