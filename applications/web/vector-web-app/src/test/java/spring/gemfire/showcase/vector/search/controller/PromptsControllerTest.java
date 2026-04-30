package spring.gemfire.showcase.vector.search.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.gemfire.showcase.vector.search.config.AiConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromptsControllerTest {
    private static final List<String> expected = List.of("Expected");

    private  PromptsController subject;

    @BeforeEach
    void setUp() {
         subject = new PromptsController(AiConfig.builder()
                 .prompts(expected).build());
    }

    @Test
    void listPrompts() {

        var actual = subject.listPrompts();

        assertThat(actual).isEqualTo(expected);

    }
}