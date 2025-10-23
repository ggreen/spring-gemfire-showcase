package spring.gemfire.showcase.vector.search.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.document.Document;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.vector.search.domain.PromptContext;
import spring.gemfire.showcase.vector.search.services.SimilaritiesService;

import java.util.List;


/**
 * * Vector Search Controller for search and evicting caching search results
 * @author Gregory Green
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("vector/search")
@Slf4j
public class VectorSearchController {

    private final ChatClient chatClient;
    private final Advisor advisor;
    private final Publisher<PromptContext> publisher;
    private final SimilaritiesService similaritiesService;

    @PostMapping
    @Cacheable("SearchResults")
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

    /**
     * Publish prompt context and evict cache for prompt
     * @param prompt the prompt
     * @param context the context
     */
    @PostMapping("prompt/context")
    @CacheEvict(value = "SearchResults", key = "#prompt")
    public void publishPromptContext(@RequestParam String prompt, @RequestParam String context) {

        publisher.send(PromptContext.builder()
                .promptText(prompt)
                .context(context).build());
    }


    @PostMapping("similarities")
    public List<Document> findSimilarities(@RequestBody String question) {
        return similaritiesService.findSimilarities(question);
    }
}
