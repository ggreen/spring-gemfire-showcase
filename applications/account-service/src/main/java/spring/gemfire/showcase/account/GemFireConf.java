package spring.gemfire.showcase.account;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import spring.gemfire.showcase.account.domain.account.Account;

@ClientCacheApplication
@EnableSecurity
@Configuration
@EnablePdx
@EnableGemfireRepositories
public class GemFireConf
{
    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(GemFireCache gemFireCache)
    {
        var bean = new ClientRegionFactoryBean<String,Account>();
        bean.setCache(gemFireCache);
        bean.setDataPolicy(DataPolicy.EMPTY);
        return bean;
    }
}
