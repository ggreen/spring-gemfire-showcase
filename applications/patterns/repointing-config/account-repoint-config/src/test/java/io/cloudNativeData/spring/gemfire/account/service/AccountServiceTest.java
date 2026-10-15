package io.cloudNativeData.spring.gemfire.account.service;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepository;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepositoryFallback;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private AccountService subject;

    private final Account account = JavaBeanGeneratorCreator.of(Account.class)
            .create();
    @Mock
    private AccountRepository repository;

    @Mock
    private AccountRepositoryFallback repositoryFallback;

    @Mock
    private CircuitBreaker readCircuitBreaker;

    @Mock
    private CircuitBreaker writeCircuitBreaker;

    @BeforeEach
    void setUp() {
        subject = new AccountService(repository,repositoryFallback, readCircuitBreaker, writeCircuitBreaker);
    }

    @Test
    void getFromRepository() {

        when(readCircuitBreaker.run(any(),any())).thenReturn(account);

        var actual = subject
                .findAccountById(account.getId());

        assertThat(actual).isEqualTo(account);
    }

    @Test
    void saveFromRepositoryFallback() {
        subject.saveAccount(account);

        verify(writeCircuitBreaker).run(any(),any());
    }


}