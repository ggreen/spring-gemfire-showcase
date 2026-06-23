package spring.gemfire.showcase.vector.search;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;

@Configuration
@EnableCachingDefinedRegions
public class GemFireConfig {

    //spring-ai-gemfire-index
    @Bean
    ClientRegionFactoryBean<?,?> spring_ai_gemfire_index(ClientCache cache){

        ClientRegionFactoryBean<?,?> factory = new ClientRegionFactoryBean<>();
        factory.setName("spring-ai-gemfire-index");
        factory.setDataPolicy(DataPolicy.NORMAL);
        factory.setCache(cache);
        return factory;
    }
}
