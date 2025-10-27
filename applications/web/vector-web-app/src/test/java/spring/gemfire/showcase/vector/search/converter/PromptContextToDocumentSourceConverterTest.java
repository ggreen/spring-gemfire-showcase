package spring.gemfire.showcase.vector.search.converter;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import spring.gemfire.showcase.vector.search.domain.PromptContext;

import static org.assertj.core.api.Assertions.assertThat;

class PromptContextToDocumentSourceConverterTest {

    private PromptContextToDocumentSourceConverter subject = new PromptContextToDocumentSourceConverter();
    private PromptContext sourceObject = JavaBeanGeneratorCreator.of(PromptContext.class).create();

    @Test
    void convert() {
        var actual = subject.convert(sourceObject);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(sourceObject.context());
    }

    @Test
    void convertNull() {
        assertThat(subject.convert(null)).isNull();
        assertThat(subject.convert(PromptContext.builder().build())).isNull();

        assertThat(subject.convert(PromptContext.builder().promptText("This is the prompt").build())).isNull();

    }
}