package spring.gemfire.showcase.account.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.service.AccountService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest
{
    private Account account  = JavaBeanGeneratorCreator.of(Account.class).create();

    @Mock
    private AccountService accountService;
    private AccountController subject;

    @BeforeEach
    void setUp() {
    subject = new AccountController(accountService);
    }

    @Test
    void save() {
    subject.save(account);
    verify(accountService).save(any(Account.class));
}

    @Test
    void findAccount() {
        when(accountService.findByAccountId(anyString())).thenReturn(account);

        assertEquals(ResponseEntity.ok(account),subject.findByAccountId(account.getId()));
    }

    @Test
    void findAccountNotFound_WhenEmpty() {

        when(accountService.findByAccountId(anyString())).thenReturn(null);

        subject = new AccountController(accountService);

        assertEquals(ResponseEntity.notFound().build(),subject.findByAccountId("doesNotExist"));

    }
}