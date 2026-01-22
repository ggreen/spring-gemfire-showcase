package io.cloudNativeData.spring.gemfire.repository;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Set;

public interface AccountPagingRepository {
    Set<String> findAllKeys();

    Iterable<Account> findAll(Collection<String> keys);
}
