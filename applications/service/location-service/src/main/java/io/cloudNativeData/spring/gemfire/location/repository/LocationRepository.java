package io.cloudNativeData.spring.gemfire.location.repository;

import io.cloudNativeData.spring.gemfire.account.domain.account.Location;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends GemfireRepository<Location,String> {
}
