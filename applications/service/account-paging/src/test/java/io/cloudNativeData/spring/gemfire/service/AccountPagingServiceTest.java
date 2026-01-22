package io.cloudNativeData.spring.gemfire.service;

import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.repository.AccountPagingRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountPagingServiceTest {

    private AccountPagingService subject;
    @Mock
    private AccountPagingRepository pagingRepository;

    private final Account account = JavaBeanGeneratorCreator.of(Account.class).create();

    @BeforeEach
    void setUp() {
        subject = new AccountPagingService(pagingRepository);
    }

    @Test
    void constructPages() {
        int expectedPageCount = 2;
        int pageSize = 2;
        PagingRequest pagingRequest = PagingRequest.builder().pageSize(pageSize).build();

        var keys = Set.of("key1", "key2", "key3","key4");


        when(pagingRepository.findAllKeys()).thenReturn(keys);

        var actual = subject.constructPages(pagingRequest);

        assertThat(actual).isNotNull();
        assertThat(actual.keys()).isNotNull();
        assertThat(actual.keys().size()).isEqualTo(expectedPageCount);
    }

    @Test
    void givenKeysAreNullThenConstructPage_Then_handle_condition() {
        var actual = subject.constructPages(PagingRequest.builder().build());
        assertThat(actual).isNotNull();
    }

    @Test
    void getAccounts() {

        Collection<Account> expected = List.of(account);
        when(pagingRepository.findAll(any())).thenReturn(expected);

        Collection<String> keys = List.of("key1", "key2", "key3");

        var page = subject.getAccounts(keys);

        assertThat(page).isEqualTo(expected);

    }
}