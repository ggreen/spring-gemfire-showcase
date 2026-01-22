package io.cloudNativeData.spring.gemfire.account.service;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.cloudNativeData.spring.gemfire.account.entity.Account;
import io.cloudNativeData.spring.gemfire.account.repostories.AccountRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDataServiceTest {
    private AccountDataService subject;

    @Mock
    private AccountRepository accountRepository;
    private Account account = JavaBeanGeneratorCreator.of(Account.class).create();

    @BeforeEach
    void setUp() {
        subject = new AccountDataService(accountRepository);
    }

    @Test
    void save() {
        subject.save(account);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void findAccount() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(account));
        assertEquals(account,subject.findByAccountId(account.getId()));
    }


    @Test
    void findAccountNotFound_WhenEmpty() {

        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());

        subject = new AccountDataService(accountRepository);

        assertNull(subject.findByAccountId("doesNotExist"));

    }
}