package io.cloudNativeData.spring.gemfire;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import spring.gemfire.showcase.account.domain.account.Account;

@ClientCacheApplication
public class GemFireConfig {

    @Bean
    ClientRegionFactoryBean<String, Account> accountRegion(ClientCache clientCache) {

        var clientRegionFactoryBean = new ClientRegionFactoryBean<String, Account>();
        clientRegionFactoryBean.setCache(clientCache);
        clientRegionFactoryBean.setName("Account");
        clientRegionFactoryBean.setDataPolicy(DataPolicy.EMPTY);
        return clientRegionFactoryBean;
    }

    @Bean
    Region<String, Account> accountRegionFromCache(ClientCache clientCache, GemfireTemplate gemfireTemplate) {
        return clientCache.getRegion("Account");
    }
}
