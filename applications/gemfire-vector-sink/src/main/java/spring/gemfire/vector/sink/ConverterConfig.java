package spring.gemfire.vector.sink;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.vector.sink.demo.DocumentSource;

import java.util.List;

@Configuration
public class ConverterConfig {

    @Bean
    Converter<DocumentSource, List<Document>> converter(){
        return documentSource -> List.of(new Document(documentSource.content()));
    }
}
