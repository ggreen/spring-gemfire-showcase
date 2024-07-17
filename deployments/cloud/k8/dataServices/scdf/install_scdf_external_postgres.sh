export ACCT_USER_NM=`kubectl get secrets/rabbitmq-default-user --template={{.data.username}} | base64 -d`
export ACCT_USER_PWD=`kubectl get secrets/rabbitmq-default-user --template={{.data.password}} | base64 -d`
helm repo add bitnami https://charts.bitnami.com/bitnami


export POSTGRES_URL="jdbc:postgresql://postgres-db:5432/postgres-db"
echo $POSTGRES_URL

export POSTGRES_USERNAME=`kubectl get secrets/postgres-db-app-user-db-secret --template={{.data.username}} | base64 -d`
echo $POSTGRES_USERNAME

export POSTGRES_PASSWORD=`kubectl get secrets/postgres-db-app-user-db-secret --template={{.data.password}} | base64 -d`

# echo $POSTGRES_PASSWORD

export ACCT_USER_NM=`kubectl get secrets/rabbitmq-default-user --template={{.data.username}} | base64 -d`
export ACCT_USER_PWD=`kubectl get secrets/rabbitmq-default-user --template={{.data.password}} | base64 -d`


#--set mariadb.enabled=false --set externalDatabase.password=$POSTGRES_PASSWORD --set externalDatabase.dataflow.url=$POSTGRES_URL --set externalDatabase.dataflow.user=$POSTGRES_USERNAME --set externalDatabase.skipper.url=$POSTGRES_URL --set externalDatabase.skipper.user=$POSTGRES_USERNAME --set externalDatabase.hibernateDialect=org.hibernate.dialect.PostgreSQLDialect  --set externalRabbitmq.enabled=true --set rabbitmq.enabled=false --set externalRabbitmq.host=rabbitmq --set externalRabbitmq.username=$ACCT_USER_NM --set externalRabbitmq.password=$ACCT_USER_PWD

helm install scdf oci://registry-1.docker.io/bitnamicharts/spring-cloud-dataflow --set mariadb.enabled=false --set externalDatabase.password=$POSTGRES_PASSWORD --set externalDatabase.dataflow.url=$POSTGRES_URL --set externalDatabase.dataflow.user=$POSTGRES_USERNAME --set externalDatabase.dataflow.username=$POSTGRES_USERNAME --set externalDatabase.skipper.url=$POSTGRES_URL --set externalDatabase.skipper.user=$POSTGRES_USERNAME --set externalDatabase.skipper.username=$POSTGRES_USERNAME --set externalDatabase.hibernateDialect=org.hibernate.dialect.PostgreSQLDialect  --set externalRabbitmq.enabled=true --set rabbitmq.enabled=false --set externalRabbitmq.host=rabbitmq --set externalRabbitmq.username=$ACCT_USER_NM --set externalRabbitmq.password=$ACCT_USER_PWD   --set server.service.type=LoadBalancer --set server.service.ports.http=9393
