package spring.gemfire.showcase.vector.search.controller;

import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.ToolCallbackProvider;
import spring.gemfire.showcase.vector.search.domain.PromptContext;
import spring.gemfire.showcase.vector.search.services.AiAnswerService;
import spring.gemfire.showcase.vector.search.services.SimilaritiesService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VectorSearchControllerTest {

    private VectorSearchController subject;

    @Mock
    private Publisher<PromptContext> publisher;
    @Mock
    private SimilaritiesService similaritiesService;
    @Mock
    private Document document;
    @Mock
    private ToolCallbackProvider tools;

    @Mock
    private AiAnswerService answerService;

    @BeforeEach
    void setUp() {
        subject = new VectorSearchController(publisher, similaritiesService,answerService);
    }

    @Test
    void search() {

        var expected = "Junit Test";
        when(answerService.answer(anyString())).thenReturn(expected);

        var actual = subject.answerPrompt("What is java?");

        assertNotNull(actual);
    }

    @Test
    void findSimilarities() {

        when(similaritiesService.findSimilarities(anyString())).thenReturn(List.of(document));

        var question = "Who";
        List<Document> actual = subject.findSimilarities(question);

        assertThat(actual).isNotEmpty();
    }

    @Test
    void publishContext() {
        var promptText = "This is the question";
        var context = "This is the answer";
        subject.publishPromptContext(promptText,context);

        verify(publisher).send(any());
    }
}