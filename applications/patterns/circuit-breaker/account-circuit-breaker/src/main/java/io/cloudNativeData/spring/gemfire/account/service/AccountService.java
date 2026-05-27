package io.cloudNativeData.spring.gemfire.account.service;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepository;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepositoryFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing account-related business operations.
 * <p>
 * This service interacts with an {@link AccountRepository} for persistent operations.
 * To ensure high availability and fault tolerance, it wraps read and write requests
 * within distinct, dedicated {@link CircuitBreaker} instances ("readCircuitBreaker" and
 * "writeCircuitBreaker"). If a database operation fails or times out, the service
 * automatically routes the request to an {@link AccountRepositoryFallback}.
 * </p>
 *
 * @author gregory green
 */
@Service
@Slf4j
public class AccountService {

    private final AccountRepository repository;
    private final AccountRepositoryFallback repositoryFallback;
    private final CircuitBreaker readCircuitBreaker;
    private final CircuitBreaker writeCircuitBreaker;

    public AccountService(AccountRepository repository, AccountRepositoryFallback repositoryFallback,
                      @Qualifier("readCircuitBreaker")
                          CircuitBreaker readCircuitBreaker,
                          @Qualifier("writeCircuitBreaker")
                          CircuitBreaker writeCircuitBreaker) {
        this.repository = repository;
        this.repositoryFallback = repositoryFallback;
        this.readCircuitBreaker = readCircuitBreaker;
        this.writeCircuitBreaker = writeCircuitBreaker;
    }

    /**
     * Retrieves an account by its unique identifier.
     * <p>
     * This operation is protected by the {@code readCircuitBreaker}. If the primary repository
     * call fails, logs the exception and attempts to recover the account data from the fallback repository.
     * </p>
     *
     * @param id the unique identifier of the account to find
     * @return the {@link Account} associated with the given ID, or {@code null} if not found in either repository
     */
    public Account findAccountById(String id) {
        var account = readCircuitBreaker.run(
                () -> repository.findById(id).orElse(null),
                e -> {
                    log.trace("Exception: {}", e);
                    return repositoryFallback.findById(id).orElse(null);

                });

        log.info("Account found: {}", account.getId());
        return account;
    }

    /**
     * Persists or updates an account.
     * <p>
     * This operation is protected by the {@code writeCircuitBreaker}. If the primary repository
     * write fails, logs the exception and routes the entity to the fallback repository (e.g., a local cache,
     * secondary database, or a queue for eventual consistency).
     * </p>
     *
     * @param account the {@link Account} entity to save
     */
    public void saveAccount(Account account) {
        writeCircuitBreaker
                .run(() -> repository.save(account),
                        e -> {
                            log.trace("Exception: {}", e);
                            return repositoryFallback.save(account);
                        });

        log.info("Account saved: {}", account.getId());
    }
}
