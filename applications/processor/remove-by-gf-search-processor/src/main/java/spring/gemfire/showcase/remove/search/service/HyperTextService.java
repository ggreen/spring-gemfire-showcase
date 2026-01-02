package spring.gemfire.showcase.remove.search.service;

import lombok.SneakyThrows;
import nyla.solutions.core.patterns.creational.Maker;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Service the added context from inputted text and various sources
 * @author gregory green
 */
@Service
public class HyperTextService {

    private final Maker<String,LuceneQuery<Object,Object>> maker;
    private final RestTemplate restTemplate;
    private final int maxSize;


    @SneakyThrows
    public HyperTextService(Maker<String, LuceneQuery<Object, Object>> maker, RestTemplate restTemplate,
                            @Value("${gemfire.url.search.text.max.size:100}") int maxSize) {
        this.maker = maker;
        this.maxSize = maxSize;
        this.restTemplate = restTemplate;
    }


    /**
     * Search for the text
     * @param text the text to search in the lucent index
     * @return query results
     */
    public LuceneQuery<Object, Object> search(String text) {
        return maker.make(text);
    }


    /**
     * Get Summary of a URL
     * @param url the URL to get the content
     * @return the summary
     */
    public String getSummary(URI url) {
        return getShortSummary(restTemplate.getForObject(url,String.class),maxSize);
    }

    private String getShortSummary(String htmlString, int maxLength) {
        // 1. Basic validation and trimming
        if (htmlString == null || htmlString.trim().isEmpty()) {
            return null;
        }

        var text = Text.parser().parseText(htmlString,"<title>","</title>");

        if(text.isEmpty() )
            return null;

        if(text.length() > maxLength)
            return text.substring(0,maxLength-1);

        return text;
    }
}
