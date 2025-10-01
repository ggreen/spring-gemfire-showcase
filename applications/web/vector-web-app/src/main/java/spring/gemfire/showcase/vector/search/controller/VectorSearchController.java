package spring.gemfire.showcase.vector.search.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("vector/search")
@Slf4j
public class VectorSearchController {

    private final ChatClient chatClient;
    private final Advisor advisor;


    @PostMapping
    public String searchPrompt(@RequestBody String prompt) {
        log.info("prompt: {}",prompt);
        var responseText = chatClient.prompt()
                .user(prompt)
                .advisors(advisor)
                .call();

        var content = responseText.content();

        log.info("responseText: {}",content);

        return content;
    }
}
