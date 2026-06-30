# GemFire for AI


# Getting Start

Clone Repo

```shell
git clone https://github.com/ggreen/spring-gemfire-showcase.git
cd spring-gemfire-showcase
```

## Build Repo

```shell
./mvnw package
```


Start GemFire

```shell
deployments/local/scripts/podman/gemfire-for-ai.sh 
```
Start GemFire Management Console (GMC) - Optional

```shell
deployments/local/scripts/podman/start-gmc-gideon-console.sh
```

Open GMC

```shell
open http://localhost:8080
```

Connect

```properties
name=gemfire
host=gf-locator
port=7070
```

Start Ollama

```shell
ollama serve
```

## Start SCDF

Download SCDF Jars (optional first time only)

- SCDF Server
- Skipper
- Shell

```shell
mkdir -p runtime/scdf
wget  --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.5/spring-cloud-dataflow-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-skipper-server/2.11.5/spring-cloud-skipper-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.11.5/spring-cloud-dataflow-shell-2.11.5.jar
```



Start Data Flow Server

(Use a new shell *from the data-orchestration-with-scdf-showcase directory*)

```shell
./deployments/local/scripts/dataflow/start-df-server.sh
```

--------------------------




START RABBITMQ!!!!!

```shell
podman run -it --rm --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4.2-management

```


Open SCDF
```shell
open http://localhost:9393/dashboard
```

## Option A: Bulk Importing Stream Apps (Recommended)

If you want to use the official pre-packaged Spring Cloud Stream RabbitMQ app starters:

Open the SCDF Dashboard (http://localhost:9393/dashboard).

Navigate to Applications -> Add Application(s).

Select Import application coordinates from an HTTP URI.

Paste the official release URL matching your SCDF version. For example:

Plaintext
https://dataflow.spring.io/rabbitmq-maven-latest
Click Import Applications.


## Registering Custom Starters

Step 1: Generate the File Paths
Before heading to the dashboard, run your echo script in your terminal to get the absolute paths. SCDF needs the full system path, not relative paths like $PWD.

Register 
```shell
echo "------   Copy The following App properties-----------"
echo processor.remove-by-gf-search=file://$PWD/applications/processor/remove-by-gf-search-processor/target/remove-by-gf-search-processor-0.0.1-SNAPSHOT.jar
echo processor.remove-by-gf-search.bootVersion=3
echo sink.gemfire-vector-sink=file://$PWD/applications/sink/gemfire-vector-sink/target/gemfire-vector-sink-0.0.1-SNAPSHOT.jar
echo sink.gemfire-vector-sink.bootVersion=3
```

Step 2: Navigate to the SCDF Applications Page
Open your browser and go to the SCDF Dashboard (usually http://localhost:9393/dashboard).

On the left navigation menu, click on Applications.

In the top right corner, click the Add Application(s) button.

Step 3: Register the Custom Processor
Select the Register one or more applications option.

Fill out the form fields for the processor:

Name: remove-by-gf-search

Type: Select Processor from the dropdown.

URI: Paste your full file URI (e.g., file:///Users/.../remove-by-gf-search-processor-0.0.1-SNAPSHOT.jar).

Click the New Application button at the bottom to open a second form row for your sink.

Step 4: Register the Custom Sink
In the new row, fill out the form fields for the sink:

Name: gemfire-vector-sink

Type: Select Sink from the dropdown.

URI: Paste your full file URI (e.g., file:///Users/.../gemfire-vector-sink-0.0.1-SNAPSHOT.jar).

Once both rows are filled out, click Register Application(s).

Click add Applications -> by properties

Please properties from "Copy The following App properties" step





## Deploy SCDF Pipeline


In SCDF  click create stream and re-create with the following

```scdf
vector-stream=http --port=7888| gemfire-vector-sink
```

Here are the step-by-step instructions to deploy your custom streaming pipeline (vector-stream=http --port=7888 | gemfire-vector-sink) using the Spring Cloud Data Flow Dashboard.

Step 1: Navigate to the Stream Creation Page
Open your browser and go to the SCDF Dashboard (http://localhost:9393/dashboard).

On the left navigation panel, click on Streams.

In the top right corner, click the Create Stream(s) button.

Step 2: Define the Stream Definition
You will see a visual canvas and a text editor box at the top.

Copy and paste your exact pipeline definition into the text editor:

Plaintext
http --port=7888 | gemfire-vector-sink
You will notice the visual canvas automatically links the http source block to your custom gemfire-vector-sink block.

Click the Create Stream button at the bottom of the page.

Step 3: Name the Stream
A dialog box will appear asking for a name.

Enter your stream name: vector-stream.

Leave the "Deploy Stream" checkbox unchecked for now (we will configure deployment properties in the next step), then click Create.

Step 4: Configure and Deploy the Stream
You will be redirected back to the Streams list page. Find your newly created vector-stream.

Click the Play icon (Deploy) next to the stream name.

You will be taken to the deployment configuration page. Scroll down to the Application Properties section to ensure your Spring Boot 3 configurations are respected:

Expand the gemfire-vector-sink section.

Ensure any specific configurations required for your custom GemFire sink (like locator host, port, or region names) are typed into the property fields.

Scroll to the bottom of the page and click the Deploy Stream button.




## Start Chat UI Web Application

```shell
java -jar applications/web/vector-web-app/target/vector-web-app-0.0.1-SNAPSHOT.jar --spring.ai.vectorstore.gemfire.host=localhost --spring.ai.vectorstore.gemfire.port=7080 --spring.ai.ollama.base-url=http://localhost:11434 --server.port=8088 --spring.ai.ollama.chat.options.model="gpt-oss:20b" --vector.service.url="http://localhost:7888"
```

Open Question HTML

```shell
open http://localhost:8088/ai.html
```

```shell
open http://localhost:8088/answer.html
```


*****************************
# Start DEMO

Ask Question

```text
Was Tanzu GemFire 10 dedicated to anyone?
```

Answer Question

- https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/

- Review logs in Data Flow


Ask Question

- Was Tanzu GemFire 10 dedicated to anyone?


---------------


Shutdown


```shell 
$GEMFIRE_HOME/bin/gfsh -e connect -e "shutdown --include-locators"
```

Kill web application