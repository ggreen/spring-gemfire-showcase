package io.cloudNativeData.spring.gemfire.account.server.account.listeners.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.server.account.listeners.domain.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,String> {
}
