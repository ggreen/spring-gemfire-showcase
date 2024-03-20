package spring.gemfire.showcase.account.repostories;

import spring.gemfire.showcase.account.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
    public interface AccountRepository
    {
        Account save(Account account);

        Optional<Account> findById(String id);
    }
