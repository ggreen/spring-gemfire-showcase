

```shell
podman run -it \
  --name pgvector \
  -e POSTGRES_USER=pgvector \
  -e POSTGRES_PASSWORD=pgvector \
  -e POSTGRES_DB=pgvector \
  -p 5432:5432 pgvector/pgvector:pg18
```


```shell
podman exec -it pgvector psql -d pgvector -U pgvector 
```

```sql
drop table vector_store;
```