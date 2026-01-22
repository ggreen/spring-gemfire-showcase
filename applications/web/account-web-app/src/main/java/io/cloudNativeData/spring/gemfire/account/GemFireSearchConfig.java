package io.cloudNativeData.spring.gemfire.account;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.lucene.LuceneQueryFactory;
import org.apache.geode.cache.lucene.LuceneServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GemFireSearchConfig {

    @Bean
    LuceneQueryFactory luceneQueryFactory(GemFireCache gemFireCache)
    {
        return LuceneServiceProvider.get(gemFireCache).createLuceneQueryFactory();
    }
}
