package spring.gemfire.showcase.vector.search.converter;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.vector.search.domain.DocumentSource;
import spring.gemfire.showcase.vector.search.domain.PromptContext;

/**
 * Converts PromptContext to DocumentSource
 * @author Gregory Green
 */
@Component
public class PromptContextToDocumentSourceConverter implements Converter<PromptContext, DocumentSource> {

    @Override
    public DocumentSource convert(PromptContext sourceObject) {
        if(sourceObject ==null || sourceObject.context() == null || sourceObject.context().isEmpty())
            return null;

        return DocumentSource.builder().content(sourceObject.context()).build();
    }
}
