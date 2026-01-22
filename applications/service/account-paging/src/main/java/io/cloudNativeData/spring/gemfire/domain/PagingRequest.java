package io.cloudNativeData.spring.gemfire.domain;

import lombok.Builder;

/**
 * The data transfer object of a request to construct pages.
 * @param pageSize the size per page
 */
@Builder
public record PagingRequest(int pageSize) {
}
