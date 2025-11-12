package spring.gemfire.showcase.mcp.server;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import nyla.solutions.core.util.Organizer;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.mcp.server.service.LookupService;

import java.util.List;
import java.util.Map;

@Configuration
public class AiConfig {

    @Bean
    public List<ToolCallback> gemfireMCPTools (Map<String,String> map) {

        var toolCallBacks = List.of(ToolCallbacks.from(new LookupService(map)));
        ToolCallingChatOptions.validateToolCallbacks(toolCallBacks);
        return toolCallBacks;
    }

    @Bean
    public List<McpServerFeatures.SyncPromptSpecification> myPrompts() {
        var prompt = new McpSchema.Prompt("greeting", "A friendly greeting prompt",
                List.of(new McpSchema.PromptArgument("name", "The name to greet", true)));

        var promptSpecification = new McpServerFeatures.SyncPromptSpecification(prompt, (exchange, getPromptRequest) -> {
            String nameArgument = (String) getPromptRequest.arguments().get("name");
            if (nameArgument == null) { nameArgument = "friend"; }
            var userMessage = new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent("Hello " + nameArgument + "! How can I assist you today?"));
            return new McpSchema.GetPromptResult("A personalized greeting message", List.of(userMessage));
        });

        return List.of(promptSpecification);
    }

}
