

```shell
deployments/local/scripts/podman/start-gemfire-external-clients.sh
```


Create region in GemFire

```shell
podman exec -it gf-locator gfsh -e "connect --locator=gf-locator[10334]" -e "create region --name=Employees --type=PARTITION --enable-statistics=true"
```


### **cURL Examples**

**Insert Employee 1**

```shell
curl -X PUT  -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","employeeId":"E001","department":"Engineering","salary":85000}' http://localhost:7080/gemfire-api/v1/Employees/E002
```
