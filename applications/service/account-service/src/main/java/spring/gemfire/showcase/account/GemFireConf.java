package spring.gemfire.showcase.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@ClientCacheApplication
@EnableSecurity
@Configuration
@EnablePdx
@EnableGemfireRepositories
public class GemFireConf
{
//    @Bean("Account")
//    ClientRegionFactoryBean<String, Account> account(ClientCache gemFireCache)
//    {
//        var bean = new ClientRegionFactoryBean<String,Account>();
//        bean.setCache(gemFireCache);
//        bean.setDataPolicy(DataPolicy.EMPTY);
//        return bean;
//    }
}
