package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.service.AccountPagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.domain.account.Account;

@RestController
@RequestMapping("accounts/paging")
@RequiredArgsConstructor
public class AccountsPagingController {

    private final AccountPagingService service;
    private Pages pages;

    @PostMapping("pages")
    public void buildPages(@RequestBody PagingRequest pagingRequest) {
        this.pages = service.constructPages(pagingRequest);

    }
    @GetMapping("/page/{pageIndex}")
    public Iterable<Account> getPage(@PathVariable int pageIndex) {
        var pageKeys = pages.keys().get(pageIndex);
        return service.getAccounts(pageKeys);
    }
    Pages getPages() {
        return pages;
    }



}
