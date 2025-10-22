package spring.gemfire.showcase.vector.search;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import spring.gemfire.showcase.vector.search.domain.DocumentSource;
import spring.gemfire.showcase.vector.search.domain.PromptContext;

import java.net.URI;

@Configuration
public class PublisherConfig {

    @Value("${vector.service.url:http://localhost:7088/functions/saveToVectorStore}")
    private URI vectorServiceUrl;

    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    Publisher<PromptContext> publisher(RestTemplate restTemplate, Converter<PromptContext, DocumentSource> converter) {
        return new Publisher<PromptContext>() {
            @Override
            public void send(PromptContext promptContext) {
                restTemplate.postForObject(vectorServiceUrl, converter.convert(promptContext), String.class);
            }
        };
    }
}
