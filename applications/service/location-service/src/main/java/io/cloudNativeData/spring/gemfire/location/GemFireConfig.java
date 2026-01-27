package io.cloudNativeData.spring.gemfire.location;

import io.cloudNativeData.spring.gemfire.account.domain.account.Location;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
@EnableGemfireCacheTransactions
public class GemFireConfig {

    @Bean("Location")
    ClientRegionFactoryBean<String, Location> location(ClientCache gemfireCache)
    {
        Interest[] interests = {new Interest(Interest.ALL_KEYS)};
        var bean= new ClientRegionFactoryBean<String,Location>();
        bean.setCache(gemfireCache);
        bean.setDataPolicy(DataPolicy.NORMAL);
        bean.setInterests(interests);
        bean.setName("Location");
        return bean;
    }

}
