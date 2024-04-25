package spring.gemfire.showcase.account.repostories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.List;


@Repository
    public interface AccountRepository
    extends CrudRepository<Account,String>
    {
        List<Account> findByNameContaining(String name);
    }
