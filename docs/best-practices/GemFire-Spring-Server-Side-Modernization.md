# Spring for GemFire version 2.0

## Modernization Guide


### Background

This document contains guidelines for upgrading systems that use Spring Boot for Tanzu GemFire or Spring Data for Tanzu GemFire from Version 1.x to 2.x.

Version 2.x of Spring Boot for Tanzu GemFire or Spring Data for Tanzu GemFire has removed Spring-based Server Configuration Annotations and utilities. Support for configuring and bootstrapping a Tanzu GemFire server using Spring is discontinued. Systems can still leverage Spring for your Tanzu GemFire client applications, servers must now be started using alternative methods such as gfsh or native GemFire Java APIs.

See the following docs for more details

https://techdocs.broadcom.com/us/en/vmware-tanzu/data-solutions/spring-boot-for-tanzu-gemfire/2-0/gf-sb-2-0/upgrading-1-x-to-2-x.html

# Server Migration Guides

For each of the following Spring Features, the following table identifies whether the feature is for clients or servers. It also provides guidance notes on how to migrate any server-related components using gfsh and or Java.


| Spring Feature                | Client/ Server    | Version SERVER 2.0  Migration Guidance                                                                                                                                                                                                                                                              |
|-------------------------------|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @EnableSsl                    | Client and Server | [gfsh] gfsh -e  "start server --name="$MEMBER_HOST_NM" --security-properties-file=$SECURITY_DIR/gfsecurity.properties                                                                                                                                                                               |
| @EnablePdx                    | Client and Server | [gfsh] configure pdx --read-serialized=true --auto-serializable-classes=".*" --disk-store                                                                                                                                                                                                           |
| @ClientCacheApplication       | Client            | N/A - no client-side changings needed                                                                                                                                                                                                                                                               |
| @CacheServerApplication       | Server            | [gfsh] ./gfsh -e "start server --name=server1 …                                                                                                                                                                                                                                                     |
| @EnableStatistics             | Client and Server | [gfsh] "start server … --statistic-archive-file=$MEMBER_STAT_FILE -J=-Dgemfire.enable-time-statistics=$ENABLE_TIME_STATISTICS --J=-D-gemfire.statistic-sampling-enabled=true --J=-Dgemfire.archive-disk-space-limit=$STAT_DISK_LIMIT_MB ​​--J=-Dgemfire.archive-file-size-limit=$STAT_FILE_LIMIT_MB |
| @EnableGemfireFunctions       | Server            | [gfsh] deploy --jar=/tmp/demo-functions-0.0.1-SNAPSHOT.jar                                                                                                                                                                                                                                          | 
 | @EnableClusterConfiguration   | Client            | N/A (N/A - no client-side changes need)                                                                                                                                                                                                                                                             |  
| @EnableCaching                | Client            | N/A  (N/A - no client-side changes need)                                                                                                                                                                                                                                                            |
| PartitionRegionsFactoryBean   | Server            | [gfsh] create region --name=Location --type=PARTITION --enable-statistics=true                                                                                                                                                                                                                      |
 | ReplicationRegionFactoryBean  | Server            | [gfsh] create region --name=Location --type=REPLICATE --enable-statistics=true                                                                                                                                                                                                                      |
| DiskStoreFactoryBean          | Client/Server     | [gfsh] create disk-store --name=name                                                                                                                                                                                                                                                                |
| EvictionAttributesFactoryBean | Server            | [gfsh] create region --name=myRegion --type=PARTITION --eviction-entry-count=512 --eviction-action=overflow-to-disk                                                                                                                                                                                 |
| AsyncEventQueueFactoryBean    | Server            | [gfsh] create async-event-queue --id="persistentAsyncQueue" --persistent=true --disk-store="diskStoreA" --parallel=true --listener=MyAsyncEventListener --listener-param=url#jdbc:db2:SAMPLE --listener-param=username#gfeadmin --listener-param=password#admin1                                    |
| Custom TTL Customer Expiry    | Server            | [gfsh] create region --name=region1 --type=REPLICATE --enable-statistics --entry-idle-time-expiration=60 --entry-idle-time-custom-expiry=com.company.mypackage.MyClass                                                                                                                              |
| GatewaySenderFactoryBean      | Server            | [gfsh] gfsh>create gateway-sender --id="sender2" --parallel=true --remote-distributed-system-id="2"                                                                                                                                                                                                 |                                                                                                                                                                                               |
| GatewayReceiverFactoryBean    | Server            | [gfsh] gfsh>create gateway-receiver --start-port=1530 --end-port=1551 \ --hostname-for-senders=gateway1.mycompany.com                                                                                                                                                                               |
| MembershipListenerAdapter     | Client/ Server    | [Java] ManagementService.getExistingManagementService(CacheFactory.getAnyInstance()).addMembershipListener(memberListener);                                                                                                                                                                         |
| GemfireLockRegistry           | Client/Server     | [java] var service = DistributedLockService.create("serviceName",CacheFactory.getAnyInstance().getDistributedSystem());                                                                                                                                                                             |
| IndexFactoryBean              | Server            | [gfsh] gfsh> create index --name=myIndex --expression=status --region=/exampleRegion                                                                                                                                                                                                                | 
| Cache Loader                  | Client/ Server    | [gfsh] deploy --jars=/var/data/lib/myLoader.jar; create region --name=r3 --cache-loader=com.example.appname.myCacheLoader{'URL':'jdbc:cloudscape:rmi:MyData'}                                                                                                                                       |
| ClientCacheConfigurer         | Client            | No client-side changes required.                                                                                                                                                                                                                                                                    |




