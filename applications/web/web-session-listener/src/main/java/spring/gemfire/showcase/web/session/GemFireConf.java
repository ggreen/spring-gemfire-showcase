package spring.gemfire.showcase.web.session;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import spring.gemfire.showcase.web.session.listener.SpringWebSessionListener;

@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true) //seconds
@EnableContinuousQueries
@EnablePdx(readSerialized = true)
@Configuration
public class GemFireConf {

    @Value("${spring.session.data.gemfire.session.region.name:ClusteredSpringSessions}")
    private String regionName;

    @Bean
    Region<String, PdxInstance> account(GemFireCache gemFireCache, SpringWebSessionListener springWebSessionListener) {
        Region<String, PdxInstance> region = gemFireCache.getRegion(regionName);

        var durable = true;

        var regionAttributes = region.getAttributesMutator();

        region.registerInterestForAllKeys();
        regionAttributes.addCacheListener(springWebSessionListener);
        return region;
    }

}