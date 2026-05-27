package io.cloudNativeData.spring.gemfire.account.repository;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.Optional;

/**
 * The account repository fallback based on an
 * open circuit breaker
 *
 * @author gregory green
 */
public interface AccountRepositoryFallback {

    Optional<Account> findById(String id);

    Account save(Account account);
}
