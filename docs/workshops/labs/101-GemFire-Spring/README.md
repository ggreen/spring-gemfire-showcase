# Spring Data for GemFire Workshop


This set of activities introduces key GemFire features for Spring Applications.
You’ll start with Spring Boot applications using Spring Data,
then explore the look aside and local/near cache patterns.
Review GemFire transactions for ACID workload with data.
Finally, understand GemFire goes beyond caching with persistence and resiliency to
used as an operational database for Spring Applications.


## Pre-requisite

- Mac/Linux
- [Podman Desktop](https://podman-desktop.io/) 1.20 or higher
- [Java 17](https://formulae.brew.sh/formula/openjdk@17) -  brew install openjdk@17
- Apache Maven 3.9.1 + (ex: brew install maven@3.9)
- [Curl](https://formulae.brew.sh/formula/curl)  (Ex: brew install curl)
- [Wget](https://formulae.brew.sh/formula/wget) (ex: brew install wget)
- git - (ex: brew install git)


Pull Docker images

```shell
podman pull gemfire/gemfire-management-console:1.4
podman pull gemfire/gemfire-all:10.2-jdk21
```


Clone Repo
```shell
git clone https://github.com/ggreen/spring-gemfire-showcase.git 
cd spring-gemfire-showcase
```

Download Apps

A Broadcom account is required in order to build the applications in this repo. If you do not have a 
Broadcom account, you can download the pre-compiled JAR files
in order to exercise the labs.

The lab instructions will reference the applications in the directory **runtime/apps**.

```shell
mkdir -p runtime/apps
wget -P runtime/apps  https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Spring-Workshop-v1/account-service-1.0.0.jar
wget -P runtime/apps  https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Spring-Workshop-v1/account-jdbc-caching-rest-service-1.0.0.jar
wget -P runtime/apps  https://github.com/ggreen/spring-gemfire-showcase/releases/download/GemFire-Spring-Workshop-v1/account-location-service-1.0.0.jar
```

# Labs

| Lab   | Description                           |
|-------|---------------------------------------|
| 01    | [01-CRUD_Reposition_Pattern.md](01-CRUD_Reposition_Pattern.md) |
| 02    |  [02-Lookup_Aside_Caching_Pattern.md](02-Lookup_Aside_Caching_Pattern.md)                         |
| 03    |  [03_Local_Near_Cache_Pattern.md](03_Local_Near_Cache_Pattern.md)                    |
| 04    |   [04_GemFire_Transaction.md](04_GemFire_Transaction.md)                  |
| 05    | [05_GemFire_Operation_Data_Store.md](05_GemFire_Operation_Data_Store.md)   |
|
