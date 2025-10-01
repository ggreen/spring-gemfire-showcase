package spring.gemfire.vector.sink.domain;

import java.net.URL;

public record DocumentSource(String content, URL[] urls) {
}
