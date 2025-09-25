package spring.gemfire.showcase.vector.search;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiClientConfig {
    @Bean
    ChatClient chatClient(ChatModel chatModel)
    {
        return ChatClient.create(chatModel);
    }

    @Bean
    QuestionAnswerAdvisor advisor(VectorStore vectorStore){
        return new QuestionAnswerAdvisor(vectorStore);
    }
}
