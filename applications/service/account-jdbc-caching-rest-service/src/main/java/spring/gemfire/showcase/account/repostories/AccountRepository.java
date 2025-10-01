package spring.gemfire.showcase.account.repostories;

import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.entity.Account;

import java.util.Optional;


@Repository
    public interface AccountRepository
    {
        Account save(Account account);

        Optional<Account> findById(String id);
    }
