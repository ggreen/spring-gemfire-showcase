package spring.gemfire.showcase.vector.search;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.EnableCachingDefinedRegions;

import java.util.Arrays;

@Configuration
@Slf4j
//@EnableCachingDefinedRegions
public class GemFireConfig {

    //spring-ai-gemfire-index
    @Bean
    ClientRegionFactoryBean<?,?> springAiGemfireIndex(ClientCache cache){

        ClientRegionFactoryBean<?,?> factory = new ClientRegionFactoryBean<>();
        factory.setName("spring-ai-gemfire-index");
        factory.setDataPolicy(DataPolicy.EMPTY);
        factory.setCache(cache);
        return factory;
    }

    @Bean
    ClientRegionFactoryBean<?,?> searchResults(ClientCache cache){
        ClientRegionFactoryBean<?,?> factory = new ClientRegionFactoryBean<>();
        factory.setName("SearchResults");
        factory.setDataPolicy(DataPolicy.EMPTY);
        factory.setCache(cache);
        return factory;
    }

    @Bean
    CommandLineRunner init(ApplicationContext ctx){

        return args -> {
            System.out.println("--- All Spring Boot Bean Names ---");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);

            for (String beanName : beanNames) {
                log.trace(beanName);
            }
        };

    }
}
