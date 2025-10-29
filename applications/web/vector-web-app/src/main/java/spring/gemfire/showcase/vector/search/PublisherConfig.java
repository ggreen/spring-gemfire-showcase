package spring.gemfire.showcase.vector.search;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import spring.gemfire.showcase.vector.search.domain.PromptContext;

import java.net.URI;

@Configuration
@Slf4j
public class PublisherConfig {

    @Value("${vector.service.url:http://localhost:7088/functions/saveToVectorStore}")
    private URI vectorServiceUrl;

    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    Publisher<PromptContext> publisher(RestTemplate restTemplate) {
        return new Publisher<PromptContext>() {
            @Override
            public void send(PromptContext promptContext) {
                log.info("Publishing in url: {} context: {}",vectorServiceUrl,promptContext);
                var headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);

                var request = new HttpEntity<String>(promptContext.context(), headers);
                restTemplate.postForObject(vectorServiceUrl, request, String.class);
            }
        };
    }
}
