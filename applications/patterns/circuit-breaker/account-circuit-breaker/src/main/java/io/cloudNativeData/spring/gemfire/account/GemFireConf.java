package io.cloudNativeData.spring.gemfire.account;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.client.Pool;
import org.apache.geode.cache.client.PoolManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
public class GemFireConf
{
    @Value("${gemfire.cluster.2.locator.host}")
    private String locatorCluster2Host;

    @Value("${gemfire.cluster.2.locator.port}")
    private int locatorCluster2Port;

    @Value("${gemfire.cluster.2.pool.name:cluster-2}")
    private String clusterPool2Name;

    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(ClientCache gemFireCache)
    {
        var bean = new ClientRegionFactoryBean<String,Account>();
        bean.setCache(gemFireCache);
        bean.setDataPolicy(DataPolicy.EMPTY);
        return bean;
    }

    @Bean("accountTemplateCluster2")
    GemfireTemplate accountRegionCluster2(ClientCache clientCache)
    {
        var poolCluster1 = PoolManager.createFactory()
                .addLocator(locatorCluster2Host, locatorCluster2Port)
                .create(clusterPool2Name);

        return new GemfireTemplate(clientCache
                .<String, Account>createClientRegionFactory(ClientRegionShortcut.PROXY)
                .setPoolName(clusterPool2Name)
                .setServerRegionName("Account")
                .create("OrdersCluster1"));

    }
}
