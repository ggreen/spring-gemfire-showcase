package spring.gemfire.showcase.account.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest
{
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PageRequest expectedPageable;

    @Mock
    private Page<Account> page;

    private AccountController subject;
    private Account account;
    private String name = "Josiah";

    @BeforeEach
    void setUp()
    {
        account = JavaBeanGeneratorCreator.of(Account.class).create();
        subject = new AccountController(accountRepository);
    }

    @Test
    @DisplayName("Given account When save Then Can get account by Id")
    void createRead()
    {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        subject.save(account);
        verify(accountRepository).save(account);
        assertEquals(account,subject.findById(account.getId()));

    }

    @Test
    @DisplayName("Given saved account When delete Then repository delete called")
    void delete()
    {
        subject.deleteById(account.getId());
        verify(accountRepository).deleteById(account.getId());
    }


    @Test
    void findByName() {
        List<Account> expected = asList(account);

        when(accountRepository.findByNameContaining(anyString())).thenReturn(expected);

        var actual = subject.findByName(name);

        assertEquals(expected, actual);

    }
}