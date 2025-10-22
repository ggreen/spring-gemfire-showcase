package spring.gemfire.showcase.vector.search.domain;

import lombok.Builder;

@Builder
public record PromptContext(String promptText,String context) {
}
