package spring.gemfire.showcase.account;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import spring.gemfire.showcase.account.entity.Account;

/*
@Configuration
@EnablePdx
@EnableGemfireRepositories
 */
@Configuration
@EnablePdx
@EnableCachingDefinedRegions
@EnableGemfireRepositories
public class GemFireConf
{
//    @Bean("AccountDbCache")
//    ClientRegionFactoryBean<String, Account> account(ClientCache gemFireCache)
//    {
//        var bean = new ClientRegionFactoryBean<String,Account>();
//        bean.setCache(gemFireCache);
//        bean.setName("AccountDbCache");
//        bean.setDataPolicy(DataPolicy.EMPTY);
//        return bean;
//    }
//    @Bean("AccountCache")
//    ClientRegionFactoryBean<String, Account> AccountCache(ClientCache gemFireCache)
//    {
//        var bean = new ClientRegionFactoryBean<String,Account>();
//        bean.setCache(gemFireCache);
//        bean.setName("AccountCache");
//        bean.setDataPolicy(DataPolicy.EMPTY);
//        return bean;
//    }


}
