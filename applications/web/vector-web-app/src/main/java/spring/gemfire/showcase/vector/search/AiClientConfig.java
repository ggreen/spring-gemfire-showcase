package spring.gemfire.showcase.vector.search;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.vector.search.services.SimilaritiesService;

@Configuration
public class AiClientConfig {
    @Bean
    ChatClient chatClient(ChatModel chatModel)
    {
        return ChatClient.create(chatModel);
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
}
