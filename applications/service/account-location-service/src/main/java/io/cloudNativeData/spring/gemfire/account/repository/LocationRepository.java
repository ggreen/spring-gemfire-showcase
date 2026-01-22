package io.cloudNativeData.spring.gemfire.account.repository;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.domain.account.Location;

@Repository
public interface LocationRepository extends GemfireRepository<Location, String> {
}
