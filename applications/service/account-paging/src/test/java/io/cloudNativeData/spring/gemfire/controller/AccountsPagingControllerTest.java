package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.service.AccountPagingService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountsPagingControllerTest {


    @Mock
    private AccountPagingService service;

    private AccountsPagingController subject;
    private final PagingRequest pagingRequest =  PagingRequest.builder().pageSize(1).build();
    private final Pages expectedPages = Pages.builder()
            .keys(List.of(List.of("11"))).build();

    private final Account account = JavaBeanGeneratorCreator.of(Account.class).create();
    @BeforeEach
    void setUp() {
        subject = new AccountsPagingController(service);
    }

    @Test
    void constructPages() {

        when(service.constructPages(pagingRequest)).thenReturn(expectedPages);

        subject.buildPages(pagingRequest);


        assertThat(subject.getPages()).isEqualTo(expectedPages);
    }

    @Test
    void getPage() {

        when(service.constructPages(any())).thenReturn(expectedPages);
        var expectedAccounts = List.of(account);
        when(service.getAccounts(any()))
                .thenReturn(expectedAccounts);

        subject.buildPages(pagingRequest);

        var actual = subject.getPage(0);
        assertThat(actual).isEqualTo(expectedAccounts);
    }
}