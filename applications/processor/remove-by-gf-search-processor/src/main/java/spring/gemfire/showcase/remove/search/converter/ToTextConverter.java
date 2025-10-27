package spring.gemfire.showcase.remove.search.converter;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ToTextConverter implements Converter<String,String> {
    private final RestTemplate restTemplate;
    @Override
    public String convert(String input) {
        if(input != null && input.trim().startsWith("http"))
            return restTemplate.getForEntity(input,String.class).getBody();

        return input;
    }
}
