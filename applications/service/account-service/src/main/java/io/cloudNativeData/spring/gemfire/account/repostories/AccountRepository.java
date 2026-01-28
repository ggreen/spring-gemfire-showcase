package io.cloudNativeData.spring.gemfire.account.repostories;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.List;


@Repository
    public interface AccountRepository
    extends GemfireRepository<Account,String>, PagingAndSortingRepository<Account,String>
    {
        Iterable<Account> findByName(String name);

        Iterable<Account> findByNameLike(String name);
    }
