package spring.gemfire.showcase.account;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import spring.gemfire.showcase.account.domain.account.Account;

@EnableCaching
@Configuration
public class GfCachingConfig {
    @Bean
    ClientRegionFactoryBean<String, Account> favoriteAccounts(GemFireCache gemFireCache)
    {
        var factory = new ClientRegionFactoryBean<String, Account>();
        factory.setCache(gemFireCache);
        factory.setName("FavoriteAccounts");
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean
    GemfireCacheManager cacheManager(GemFireCache gemfireCache) {

        var cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        return cacheManager;
    }
}
