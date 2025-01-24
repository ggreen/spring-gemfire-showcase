# web-session-listener


The example application illustrates how to track user session duration times

This example works with spring security and GemFire support for HTTP sessions. See [account-web-app](../../account-web-app)

The web session listener is a standalone application. It registers interests to GemFire for changes to the region where GemFire-and spring stores the user sessions data.

The listener is provided the sessions detail when a users logs into the session or the sessions data is destroyed. In this example the  listener is durable. This means the events are an at least once delivery. Events are stored for later delivery if the app is not running. Non durable  listener only Receives current events.

The region keys contains the session ID. The user session data is stored in PDX. Spring sessions maintains the creation timestamp and the user name in the principal name field.

Note the session destroy event only contain the sessions id. This would need to be correlated with the creations event to match the username on provided with the principal name attribute. For example, if you create a database table with the username, creation id and session during the creation event, then you can select the creation time by session id to determine the duration when the session entry is destroyed.

A similar approach of using a standalone listen  can be used for web applications that do not use spring security with Gemfire HTTP sessions support.

There is an overheard of maintain changes events for the listener app. Session events can build up on the server. Durable listener events also take up additional disk space.


# Demo Instructions

Run Script to run GemFire in Docker

```shell
./deployments/local/scripts/docker/start-docker-gemfire.sh
```


```shell
docker exec -it gf-locator gfsh 
```

```shell
create region --name=ClusteredSpringSessions --type=PARTITION
```

# Testing


Example log output

```shell
2025-01-24T15:09:52.570-05:00  INFO 5936 --- [web-session-listener] [7392 port 40404] s.w.s.s.DemoOnlyUserSessionReportService : User: user Duration:80114 milliseconds 

```