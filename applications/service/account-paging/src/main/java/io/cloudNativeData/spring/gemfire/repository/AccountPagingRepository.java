package io.cloudNativeData.spring.gemfire.repository;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.Collection;
import java.util.Set;

/**
 * Repository for paging operations
 *  @author gregory green
 */
public interface AccountPagingRepository {

    /**
     * Get all keys
     * @return the all account keys
     */
    Set<String> findAllKeys();

    /**
     * Find all accounts with provided paged keys
     * @param keys the key for a page
     * @return the match accounts
     */
    Iterable<Account> findAll(Collection<String> keys);
}
