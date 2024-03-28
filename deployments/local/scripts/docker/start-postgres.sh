mkdir -p /Users/devtools/repositories/RDBMS/PostgreSQL/pg-docker
docker  run --name postgresql  --network gemfire-cache --rm -it -p 5432:5432 -e ALLOW_EMPTY_PASSWORD=yes -v /Users/devtools/repositories/RDBMS/PostgreSQL/pg-docker:/bitnami/postgresql bitnami/postgresql:latest
