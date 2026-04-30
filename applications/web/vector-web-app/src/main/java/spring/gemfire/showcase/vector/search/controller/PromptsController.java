package spring.gemfire.showcase.vector.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gemfire.showcase.vector.search.config.AiConfig;

import java.util.List;

@RestController
@RequestMapping("prompts")
public class PromptsController {

    private final List<String> prompts;

    public PromptsController(AiConfig aiConfig) {
        this.prompts = aiConfig.getPrompts();
    }

    @GetMapping
    public List<String> listPrompts() {
        return prompts;
    }
}
