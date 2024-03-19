package spring.gemfire.showcase.account;

import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.domain.account.Location;
import spring.gemfire.showcase.account.listener.cache.VMwareAccountCacheListener;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.InterestResultPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;

@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true) //seconds
@EnableContinuousQueries
@Configuration
public class GemFireConf {
    @Value("${spring.data.gemfire.cache.client.durable-client-id}")
    private String durableClientId;

    @Value("${spring.data.gemfire.cache.client.durable-client-timeout}")
    private int durableClientTimeout;


    @Value("${spring.data.gemfire.pool.subscription-redundancy}")
    private int subscriptionRedundancy;

    @Value("${keyInterestRegExp}")
    private String keyInterestRegExp;

    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(GemFireCache gemFireCache) {
        var region = new ClientRegionFactoryBean<String, Account>();

        region.setCache(gemFireCache);
        region.setRegionName("Account");
        region.setDataPolicy(DataPolicy.EMPTY);
        var durable = true;
        var interests = new Interest[]{new Interest<String>(keyInterestRegExp, InterestResultPolicy.KEYS, durable)};

        region.setInterests(interests);
        region.setStatisticsEnabled(true);

        var listeners = new CacheListener[]{new VMwareAccountCacheListener()};
        region.setCacheListeners(listeners);

        return region;
    }

    @Bean("Location")
    ClientRegionFactoryBean<String, Location> location(GemFireCache gemFireCache, @Qualifier("accountTemplate") GemfireTemplate accountTemplate )
    {
        var region = new ClientRegionFactoryBean<String,Location>();
        region.setCache(gemFireCache);
        region.setRegionName("Location");
        region.setDataPolicy(DataPolicy.EMPTY);
        var durable = true;
        var interest = new Interest[]{new Interest(".*", InterestResultPolicy.KEYS,durable)};
        region.setInterests(interest);
        region.setStatisticsEnabled(true);

        return region;
    }
}