package spring.gemfire.showcase.vector.search.converter;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.vector.search.domain.PromptContext;

/**
 * Converts PromptContext to DocumentSource
 * @author Gregory Green
 */
@Component
public class PromptContextToDocumentSourceConverter implements Converter<PromptContext, String> {

    @Override
    public String convert(PromptContext sourceObject) {
        if(sourceObject ==null || sourceObject.context() == null || sourceObject.context().isEmpty())
            return null;

        return sourceObject.context();
    }
}
