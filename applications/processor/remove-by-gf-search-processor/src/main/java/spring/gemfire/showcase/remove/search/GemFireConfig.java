package spring.gemfire.showcase.remove.search;

import nyla.solutions.core.patterns.creational.Maker;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.apache.geode.cache.lucene.LuceneQueryFactory;
import org.apache.geode.cache.lucene.LuceneService;
import org.apache.geode.cache.lucene.LuceneServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import spring.gemfire.showcase.remove.search.properties.RemoveSearchProperties;

@Configuration
@ClientCacheApplication
public class GemFireConfig {

    @Bean
    LuceneService luceneService(ClientCache clientCache)
    {
        return  LuceneServiceProvider.get(clientCache);
    }

    @Bean
    LuceneQueryFactory luceneQueryFactory(LuceneService luceneService)
    {
        return luceneService.createLuceneQueryFactory();
    }

    @Bean
    Maker<String, LuceneQuery<Object, Object>> maker(RemoveSearchProperties properties, LuceneQueryFactory luceneQueryFactory)
    {
        return query -> luceneQueryFactory.create(
                properties.getIndexName(),
                properties.getRegionName(),
                query,
                properties.getDefaultField());
    }

    @Bean
    Region<Object,Object> region(ClientCache clientCache,RemoveSearchProperties properties){
        return clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY)
                .create(properties.getRegionName());
    }
}
