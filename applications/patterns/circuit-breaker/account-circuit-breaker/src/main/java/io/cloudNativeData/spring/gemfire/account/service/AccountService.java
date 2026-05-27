package io.cloudNativeData.spring.gemfire.account.service;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepository;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepositoryFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

/**
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

    public Account findAccountById(String id) {
        return readCircuitBreaker.run(
                () -> repository.findById(id).orElse(null),
                e -> {
                    log.warn("Exception: {}", e);
                    return repositoryFallback.findById(id).orElse(null);

                });
    }

    public void saveAccount(Account account) {
        writeCircuitBreaker
                .run(() -> repository.save(account),
                        e -> {
                            log.warn("Exception: {}", e);
                            return repositoryFallback.save(account);
                        });
    }
}
