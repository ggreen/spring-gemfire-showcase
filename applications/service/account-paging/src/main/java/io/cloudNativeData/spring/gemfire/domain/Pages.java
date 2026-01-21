package io.cloudNativeData.spring.gemfire.domain;

import lombok.Builder;

import java.util.Collection;
import java.util.List;

@Builder
public record Pages(List<Collection<String>> keys) {
}
