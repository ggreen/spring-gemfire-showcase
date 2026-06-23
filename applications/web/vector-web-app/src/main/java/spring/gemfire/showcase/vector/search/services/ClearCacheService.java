package spring.gemfire.showcase.vector.search.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClearCacheService {


    private final GemfireTemplate vectorStoreTemplate;
    private final GemfireTemplate searchResultsTemplate;

    public ClearCacheService(@Qualifier("springAiGemfireIndexTemplate")
                             GemfireTemplate vectorStoreTemplate,
                             @Qualifier("searchResultsTemplate")
                             GemfireTemplate searchResultsTemplate) {
        this.vectorStoreTemplate = vectorStoreTemplate;
        this.searchResultsTemplate = searchResultsTemplate;
    }

    public void clearCache() {

        log.info("Clearing all cache entries");
        var vectorStoreKeys = vectorStoreTemplate.getRegion().keySetOnServer();

        log.info("Clearing vector store keys: {}", vectorStoreKeys);
        vectorStoreTemplate.removeAll(vectorStoreKeys);


        var searchResultsKeys = searchResultsTemplate.getRegion().keySetOnServer();
        log.info("Clearing searchResults  keys: {}", searchResultsKeys);

        searchResultsTemplate.removeAll(searchResultsKeys);



    }
}
