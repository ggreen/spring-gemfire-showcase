package spring.gemfire.showcase.account;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.domain.account.Location;

@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
public class GemFireConfig {

    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(ClientCache gemfireCache)
    {
        var bean= new ClientRegionFactoryBean<String,Account>();
        bean.setCache(gemfireCache);
        bean.setDataPolicy(DataPolicy.NORMAL);
        bean.setInterests(new Interest[]{Interest.newInterest(Interest.ALL_KEYS)});
        bean.setName("Account");
        return bean;
    }

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
