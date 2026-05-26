
kill -STOP < Gemfire -PID> and resumed it using kill -CONT < Gemfire -PID>.


kill -STOP 11584

kill -CONT 11584


Error 


```text
2026-05-26T14:43:10.785-04:00  INFO 71210 --- [           main] .s.b.a.l.ConditionEvaluationReportLogger : 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2026-05-26T14:43:10.792-04:00 ERROR 71210 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.data.gemfire.GemfireCancellationException: Pool PoolImpl@606347342 name=DEFAULT is shut down
	at org.springframework.data.gemfire.GemfireCacheUtils.convertGemfireAccessException(GemfireCacheUtils.java:127) ~[spring-data-4.0-gemfire-10.2-2.0.0.jar:na]
	at org.springframework.data.gemfire.GemfireAccessor.convertGemFireAccessException(GemfireAccessor.java:85) ~[spring-data-4.0-gemfire-10.2-2.0.0.jar:na]
	at org.springframework.data.gemfire.GemfireTemplate.put(GemfireTemplate.java:197) ~[spring-data-4.0-gemfire-10.2-2.0.0.jar:na]
	at org.springframework.data.gemfire.repository.support.SimpleGemfireRepository.save(SimpleGemfireRepository.java:141) ~[spring-data-4.0-gemfire-10.2-2.0.0.jar:na]
	at jdk.internal.reflect.GeneratedMethodAccessor181.invoke(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359) ~[spring-aop-7.0.2.jar:7.0.2]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:278) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:169) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:545) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:290) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:690) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) ~[spring-aop-7.0.2.jar:7.0.2]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:171) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:146) ~[spring-data-commons-4.0.1.jar:4.0.1]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) ~[spring-aop-7.0.2.jar:7.0.2]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:222) ~[spring-aop-7.0.2.jar:7.0.2]
	at jdk.proxy2/jdk.proxy2.$Proxy125.save(Unknown Source) ~[na:na]
	at io.cloudNativeData.spring.gemfire.account.PutAndGets.run(PutAndGets.java:42) ~[classes/:na]
	at org.springframework.boot.SpringApplication.lambda$callRunner$0(SpringApplication.java:788) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.util.function.ThrowingConsumer$1.acceptWithException(ThrowingConsumer.java:82) ~[spring-core-7.0.2.jar:7.0.2]
	at org.springframework.util.function.ThrowingConsumer.accept(ThrowingConsumer.java:60) ~[spring-core-7.0.2.jar:7.0.2]
	at org.springframework.util.function.ThrowingConsumer$1.accept(ThrowingConsumer.java:86) ~[spring-core-7.0.2.jar:7.0.2]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:800) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:788) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.boot.SpringApplication.lambda$callRunners$0(SpringApplication.java:776) ~[spring-boot-4.0.1.jar:4.0.1]
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183) ~[na:na]
	at java.base/java.util.stream.SortedOps$SizedRefSortingSink.end(SortedOps.java:357) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]
	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150) ~[na:na]
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]
	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596) ~[na:na]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:776) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:328) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1365) ~[spring-boot-4.0.1.jar:4.0.1]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1354) ~[spring-boot-4.0.1.jar:4.0.1]
	at io.cloudNativeData.spring.gemfire.account.AccountCircuitBreakerApp.main(AccountCircuitBreakerApp.java:9) ~[classes/:na]
Caused by: org.apache.geode.distributed.PoolCancelledException: Pool PoolImpl@606347342 name=DEFAULT is shut down, caused by java.net.SocketTimeoutException: Read timed out
	at org.apache.geode.cache.client.internal.PoolImpl$Stopper.generateCancelledException(PoolImpl.java:1429) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.PoolImpl.generatePoolOrCacheCancelledException(PoolImpl.java:1476) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.PoolImpl.access$100(PoolImpl.java:85) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.PoolImpl$PoolOrCacheStopper.generateCancelledException(PoolImpl.java:1403) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.CancelCriterion.checkCancelInProgress(CancelCriterion.java:108) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.handleException(OpExecutorImpl.java:537) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.handleException(OpExecutorImpl.java:528) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.execute(OpExecutorImpl.java:174) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.execute(OpExecutorImpl.java:129) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.PoolImpl.execute(PoolImpl.java:819) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.PutOp.execute(PutOp.java:92) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.ServerRegionProxy.put(ServerRegionProxy.java:158) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.serverPut(LocalRegion.java:3134) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.cacheWriteBeforePut(LocalRegion.java:3250) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.ProxyRegionMap.basicPut(ProxyRegionMap.java:240) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.virtualPut(LocalRegion.java:5740) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.virtualPut(LocalRegion.java:5718) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegionDataView.putEntry(LocalRegionDataView.java:109) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.basicPut(LocalRegion.java:5155) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.validatedPut(LocalRegion.java:1730) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.LocalRegion.put(LocalRegion.java:1717) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.AbstractRegion.put(AbstractRegion.java:440) ~[gemfire-core-10.2.0.jar:na]
	at org.springframework.data.gemfire.GemfireTemplate.put(GemfireTemplate.java:194) ~[spring-data-4.0-gemfire-10.2-2.0.0.jar:na]
	... 38 common frames omitted
Caused by: java.net.SocketTimeoutException: Read timed out
	at java.base/sun.nio.ch.NioSocketImpl.timedRead(NioSocketImpl.java:288) ~[na:na]
	at java.base/sun.nio.ch.NioSocketImpl.implRead(NioSocketImpl.java:314) ~[na:na]
	at java.base/sun.nio.ch.NioSocketImpl.read(NioSocketImpl.java:355) ~[na:na]
	at java.base/sun.nio.ch.NioSocketImpl$1.read(NioSocketImpl.java:808) ~[na:na]
	at java.base/java.net.Socket$SocketInputStream.read(Socket.java:966) ~[na:na]
	at org.apache.geode.internal.cache.tier.sockets.Message.fetchHeader(Message.java:829) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.tier.sockets.Message.readHeaderAndBody(Message.java:676) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.internal.cache.tier.sockets.Message.receive(Message.java:1153) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.AbstractOp.attemptReadResponse(AbstractOp.java:215) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.AbstractOp.attempt(AbstractOp.java:385) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.ConnectionImpl.execute(ConnectionImpl.java:334) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.pooling.PooledConnection.execute(PooledConnection.java:334) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.executeWithPossibleReAuthentication(OpExecutorImpl.java:781) ~[gemfire-core-10.2.0.jar:na]
	at org.apache.geode.cache.client.internal.OpExecutorImpl.execute(OpExecutorImpl.java:170) ~[gemfire-core-10.2.0.jar:na]
	... 53 common frames omitted
```