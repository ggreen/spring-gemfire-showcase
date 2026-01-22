package io.cloudNativeData.spring.gemfire.domain;

import lombok.Builder;

import java.util.Collection;
import java.util.List;

/**
 * Represents pages of adata
 * @param keys the page keys
 * @author gregory green
 */
@Builder
public record Pages(List<Collection<String>> keys) {
}
