package io.cloudNativeData.spring.gemfire.account.sink.repostories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.List;


@Repository
    public interface AccountRepository
    extends CrudRepository<Account,String>
    {
        List<Account> findByNameContaining(String name);
    }
