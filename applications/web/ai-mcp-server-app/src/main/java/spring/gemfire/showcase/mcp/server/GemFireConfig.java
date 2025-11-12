package spring.gemfire.showcase.mcp.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;

import java.util.Map;

@Configuration
@ClientCacheApplication
@Slf4j
public class GemFireConfig {

    @Value("${gemfire.region.name:LookUp}")
    private String regionName;

    @Bean
    Map<String, String> map(ClientCache clientCache){

        log.info("Creating region/map: {}",regionName);
        ClientRegionFactory<String, String> factory = clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY);
        return factory.create(regionName);
    }
}
