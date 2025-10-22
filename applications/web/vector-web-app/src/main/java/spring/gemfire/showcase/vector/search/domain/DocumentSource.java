package spring.gemfire.showcase.vector.search.domain;

import lombok.Builder;

import java.net.URL;

@Builder
public record DocumentSource(String content, URL[] urls) {
}
