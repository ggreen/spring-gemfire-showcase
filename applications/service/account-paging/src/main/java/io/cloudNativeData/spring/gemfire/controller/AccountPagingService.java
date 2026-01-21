package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.repository.AccountPagingRepository;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.util.Organizer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.Collection;

@RestController
@RequestMapping("account/paging")
@RequiredArgsConstructor
public class AccountPagingService {

    private final AccountPagingRepository pagingRepository;

    @PostMapping("construct/pages")
    public Pages constructPages(@RequestBody PagingRequest pagingRequest) {

        var keys = pagingRepository.findAllKeys();

        var keyPages = Organizer.change().toPages(keys, pagingRequest.pageSize());

        return Pages.builder().keys(keyPages).build();
    }


    @PostMapping("page")
    public Iterable<Account> getAccounts(@RequestBody Collection<String> keys) {
        return pagingRepository.findAll(keys);
    }
}
