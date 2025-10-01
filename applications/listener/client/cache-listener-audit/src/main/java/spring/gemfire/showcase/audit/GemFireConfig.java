package spring.gemfire.showcase.audit;

import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.InterestResultPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import spring.gemfire.showcase.audit.listener.AuditCacheListener;

@Configuration
@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true)
public class GemFireConfig {

    @Value("${audit.region.name}")
    private String regionName;

    @Value("${audit.region.key.reg.expression:.*}")
    private String keyRegularExpression;

    @Bean("Employees")
    ClientRegionFactoryBean<Object,Object> employees(ClientCache clientCache, AuditCacheListener auditCacheListener){
        var region = new ClientRegionFactoryBean<Object,Object>();
        region.setCache(clientCache);
        region.setRegionName(regionName);
        region.setDataPolicy(DataPolicy.EMPTY);

        var durable = true;
        var interests = new Interest[]{new Interest<String>(keyRegularExpression, InterestResultPolicy.KEYS, durable)};
        region.setInterests(new Interest[]{Interest.newInterest(keyRegularExpression)});
        region.setStatisticsEnabled(true);

        region.setCacheListeners(new CacheListener[]{auditCacheListener});

        return region;
    }


}
