package spring.gemfire.showcase.account;

import org.apache.geode.cache.*;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionService;
import org.apache.geode.distributed.ServerLauncher;
import org.apache.geode.pdx.PdxSerializer;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.function.NoOpFunction;

/**
 * Provides examples Spring configurations from embedding GemFire
 *
 * @author gregory green
 */
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

    @Value("${gemfire.pdx.disk.store:PDX_DS}")
    private String pdxDiskStore;

    @Value("${gemfire.statistic.archive.file}")
    private String statisticArchiveFile;

    @Value("${gemfire.pdx.archive.disk.space.limit:5}")
    private String archiveDiskSpaceLimit;

    @Value("${gemfire.pdx.archive.file.size.limit:5}")
    private String archiveFileSizeLimit;

    @Value("${gemfire.partitioned.persisted.disk.store.name:partitionedPersistedDiskStoreName}")
    private String partitionedPersistedDiskStoreName;

    @Value("${gemfire.pdx.disk.store.name:PDX_STORE}")
    private String pdxDataStoreName;

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
                .set("statistic-sampling-enabled","true")
                .set("statistic-archive-file",statisticArchiveFile)
                .set("archive-disk-space-limit",archiveDiskSpaceLimit)
                .set("archive-file-size-limit",archiveFileSizeLimit)
                .setWorkingDirectory(workingDirectory)
                .setPdxReadSerialized(readPdxSerialized)
                .setPdxDiskStore(pdxDiskStore)
                .setPdxSerializer(pdxSerializer)
                .build();

        serverLauncher.start();

        return serverLauncher;
    }

    @Bean
    Region<String, Account> partitioned(Cache cache)
    {
        Region<String, Account>  region  =  (Region)cache.createRegionFactory(RegionShortcut.PARTITION)
                .create("Account");
        return region;
    }


    @Bean
    DiskStoreFactory diskStoreFactory(Cache cache)
    {
        return cache.createDiskStoreFactory();
    }

    @Bean
    DiskStore pdxDiskStoreNameDiskStore(Cache cache, DiskStoreFactory diskStoreFactory)
    {
        return diskStoreFactory.create(pdxDataStoreName);
    }

    @Bean
    DiskStore partitionedPersistedDiskStore(Cache cache, DiskStoreFactory diskStoreFactory)
    {
        return diskStoreFactory.create(partitionedPersistedDiskStoreName);
    }

    @Bean
    Region<String, Account> partitioned_persisted(Cache cache, @Qualifier("partitionedPersistedDiskStore") DiskStore diskStore)
    {
        Region<String, Account>  region  =  (Region)cache.createRegionFactory(RegionShortcut.PARTITION_PERSISTENT)
                .setDiskStoreName(diskStore.getName())
                .create("Account_persisted");
        return region;
    }

    @Bean
    Region<String, Account> replicated(Cache cache)
    {
        Region<String, Account>  region  =  (Region)cache.createRegionFactory(RegionShortcut.REPLICATE)
                .create("Account_replicated");
        return region;
    }


    @Bean
    Function<Object[]> function(Cache cache)
    {
        var function = new NoOpFunction();
        FunctionService.registerFunction(function);
        return function;
    }
}
