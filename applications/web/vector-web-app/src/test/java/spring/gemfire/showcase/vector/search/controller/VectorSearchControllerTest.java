package spring.gemfire.showcase.vector.search.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VectorSearchControllerTest {

    @Mock
    private ChatClient chatClient;
    private VectorSearchController subject;
    @Mock
    private Advisor advisor;

    @Mock
    private ChatClient.ChatClientRequestSpec prompt;
    @Mock
    private ChatClient.ChatClientRequestSpec user;
    @Mock
    private ChatClient.CallResponseSpec callResponse;
    @Mock
    private ChatClient.ChatClientRequestSpec advisors;

    @BeforeEach
    void setUp() {
        subject = new VectorSearchController(chatClient,advisor);
    }

    @Test
    void search() {

        var expected = "Junit Test";
        when(chatClient.prompt()).thenReturn(prompt);
        when(prompt.user(anyString())).thenReturn(user);
        when(user.advisors(any(Advisor.class))).thenReturn(advisors);
        when(advisors.call()).thenReturn(callResponse);
        when(callResponse.content()).thenReturn(expected);


        var actual = subject.searchPrompt("What is java?");

        assertNotNull(actual);
    }
}