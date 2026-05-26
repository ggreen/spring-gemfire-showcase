package io.cloudNativeData.spring.gemfire.account.repository;


import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends GemfireRepository<Account,String> {

}
