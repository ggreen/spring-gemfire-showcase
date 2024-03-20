
cd $GEMFIRE_HOME/bin

./gfsh -e "connect" -e "connect"  -e "create region --name=Account --type=PARTITION --enable-statistics=true"


```shell script
docker tag account-rest-service:0.0.1-SNAPSHOT nyla/account-rest-service:0.0.1-SNAPSHOT 
docker login
docker push nyla/account-rest-service:0.0.1-SNAPSHOT

```