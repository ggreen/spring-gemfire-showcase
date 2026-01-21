package io.cloudNativeData.spring.gemfire.domain;

import lombok.Builder;

@Builder
public record PagingRequest(int pageSize) {
}
