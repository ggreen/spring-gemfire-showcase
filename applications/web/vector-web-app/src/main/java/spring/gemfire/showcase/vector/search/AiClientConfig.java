package spring.gemfire.showcase.vector.search;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.vector.search.services.AiAnswerService;
import spring.gemfire.showcase.vector.search.services.SimilaritiesService;

import java.util.List;

@Configuration
public class AiClientConfig {
    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder)
    {
        return chatClientBuilder
                .build();
    }

    @Bean
    QuestionAnswerAdvisor advisor(VectorStore vectorStore){

        SearchRequest searchRequest = SearchRequest
                .builder()
                .similarityThreshold(.75)
                .build();

        return QuestionAnswerAdvisor
                .builder(vectorStore)
                .searchRequest(searchRequest).build();
    }

    @Bean
    SimilaritiesService similaritiesService(VectorStore vectorStore)
    {
        return vectorStore::similaritySearch;

    }

    @Bean
    AiAnswerService answerService(ChatClient chatClient, List<Advisor> advisors)
    {
        return prompt -> chatClient.prompt()
                .user(prompt)
                //.toolCallbacks(tools)
                .advisors(advisors) //use GemFire vectorDB
                .call().content();
    }
}
