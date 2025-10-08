package spring.gemfire.showcase.audit;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GemFireConfig {

    @Value("${audit.region.name}")
    private String regionName;

    @Value("${audit.region.key.reg.expression:.*}")
    private String keyRegularExpression;

    @Value("${audit.durable:true}")
    private boolean durable;

    @Bean("AuditRegion")
    ClientRegionFactoryBean<Object,Object> auditRegion(ClientCache clientCache, AuditCacheListener auditCacheListener){
        var region = new ClientRegionFactoryBean<Object,Object>();
        region.setCache(clientCache);
        region.setRegionName(regionName);
        region.setDataPolicy(DataPolicy.EMPTY);

        log.info("Subscribing to keys matching regular expression: {}, durable: {}", keyRegularExpression,durable);
        var interests = new Interest[]{new Interest<String>(keyRegularExpression, InterestResultPolicy.KEYS, durable)};
        region.setInterests(interests);
        region.setStatisticsEnabled(true);

        region.setCacheListeners(new CacheListener[]{auditCacheListener});

        return region;
    }


}
