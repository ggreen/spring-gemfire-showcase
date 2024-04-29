
processor.account-csv=file:///Users/Projects/VMware/Tanzu/TanzuData/TanzuGemFire/dev/spring-gemfire-showcase/applications/account-csv-processor/target/account-csv-processor-0.0.1-SNAPSHOT.jar


```shell
pdx=http --port=8801 | gemfire --gemfire.region.regionName=Account --gemfire.consumer.keyExpression="payload.getField('id')" --gemfire.consumer.json="true" --gemfire.pool.host-addresses="localhost:10334"
```

```shell
curl -X 'POST' \
'http://localhost:8801' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"id": "3",
"name": "Account 03"
}';echo
```



````shell
curl -X 'POST' \
'http://localhost:8801' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "@type" : "spring.gemfire.showcase.account.domain.account.Account",
"id": "3",
"name": "Account 03"
}';echo
````


````shell
curl -X 'POST' \
'http://localhost:8801' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{ "@type" : "spring.gemfire.showcase.account.domain.account.Account",
"__GEMFIRE_JSON" : "spring.gemfire.showcase.account.domain.account.Account",
"id": "4",
"name": "Account 04"
}';echo
````


Create new scratch file from selection
