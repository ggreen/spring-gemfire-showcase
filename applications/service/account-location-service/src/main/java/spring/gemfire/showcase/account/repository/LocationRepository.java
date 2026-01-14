package spring.gemfire.showcase.account.repository;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.domain.account.Location;

@Repository
public interface LocationRepository extends GemfireRepository<Location, String> {
}
