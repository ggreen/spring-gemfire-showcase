package io.cloudNativeData.spring.gemfire.account.repostories;

import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.entity.Account;

import java.util.Optional;


@Repository
    public interface AccountRepository
    {
        Account save(Account account);

        Optional<Account> findById(String id);
    }
