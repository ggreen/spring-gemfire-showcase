package spring.gemfire.showcase.account;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.apache.geode.distributed.ServerLauncher;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.account.domain.account.Account;


@Configuration
public class GemFireConf
{
    @Value("${gemfire.server.name}")
    private String serverName;

    @Value("${gemfire.start-locator}")
    private String startLocator;

    @Value("${spring.data.gemfire.pool.locators}")
    private String locators;

    @Value("${gemfire.async.event.queue.id}")
    private String asyncEventQueueId;

    @Value("${gemfire.pdx.patterns:.*}")
    private String pdxClassPatterns;

    @Value("${gemfire.server.port:40404}")
    private Integer serverPort;

    @Value("${gemfire.read.pdx.serialize:false}")
    private boolean readPdxSerialized;

    @Value("${gemfire.working.dir}")
    private String workingDirectory;

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
        var serverLauncher = new ServerLauncher.Builder()
                .setMemberName(serverName)
                .setServerPort(serverPort)
                .set("locators",locators)
                .setWorkingDirectory(workingDirectory)
                .setPdxReadSerialized(readPdxSerialized)
                .setPdxSerializer(pdxSerializer)
                .build();

        serverLauncher.start();

        return serverLauncher;
    }

    @Bean
    Region<String, Account> region(Cache cache)
    {
        Region<String, Account>  region  =  (Region)cache.createRegionFactory(RegionShortcut.PARTITION)
                .create("Account");
        return region;
    }

}
