package spring.gemfire.showcase.account;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.distributed.ServerLauncher;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.server.listeners.AccountAsyncEventListener;

@Configuration
@EnableGemfireRepositories
public class GemFireConf
{
    @Value("${gemfire.name}")
    private String serverName;

    @Value("${gemfire.start-locator}")
    private String startLocator;

    @Value("${gemfire.locators}")
    private String locators;

    @Value("${gemfire.async.event.queue.id}")
    private String asyncEventQueueId;

    @Value("${gemfire.pdx.patterns:.*}")
    private String pdxClassPatterns;

    @Bean
    Cache cacheFactory(ServerLauncher launcher)
    {
        return CacheFactory.getAnyInstance();
    }

    @Bean
    PdxSerializer serializer()
    {
        return new ReflectionBasedAutoSerializer(pdxClassPatterns);
    }

    @Bean
    ServerLauncher builder(PdxSerializer pdxSerializer)
    {
        var builder = new ServerLauncher.Builder()
                .setMemberName(serverName)
                .setServerPort(40405)
                .set("jmx-manager", "true")
                .set("jmx-manager-start", "true")
                .set("log-file", "")
                .set("locators",locators)
                .setPdxSerializer(pdxSerializer);

        if(startLocator != null && startLocator.length() > 0)
            builder = builder.set("start-locator", startLocator);

        var serverLauncher =  builder.build();

        serverLauncher.start();

        return serverLauncher;
    }

    @Bean
    AsyncEventListener listener()
    {
        return new AccountAsyncEventListener();
    }
    @Bean
    Region<String, Account> region(Cache cache, AsyncEventListener listener)
    {
        var queue = cache.createAsyncEventQueueFactory().create(asyncEventQueueId,listener);
        Region<String, Account>  region  =  (Region)cache.createRegionFactory(RegionShortcut.PARTITION)
                .addAsyncEventQueueId(asyncEventQueueId)
                .create("Account");

        return region;
    }






}
