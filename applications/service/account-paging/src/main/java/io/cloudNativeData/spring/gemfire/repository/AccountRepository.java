package io.cloudNativeData.spring.gemfire.repository;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.domain.account.Account;

@Repository
public interface AccountRepository extends GemfireRepository<Account, String> {
}
