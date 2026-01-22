package io.cloudNativeData.spring.gemfire.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

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
