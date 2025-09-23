package spring.gemfire.showcase.account.server.account.listeners.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.server.account.listeners.domain.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,String> {
}
