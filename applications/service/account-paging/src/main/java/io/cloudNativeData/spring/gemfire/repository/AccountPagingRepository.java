package io.cloudNativeData.spring.gemfire.repository;

import spring.gemfire.showcase.account.domain.account.Account;

import java.util.Collection;
import java.util.Set;

public interface AccountPagingRepository {
    Set<String> findAllKeys();

    Iterable<Account> findAll(Collection<String> keys);
}
