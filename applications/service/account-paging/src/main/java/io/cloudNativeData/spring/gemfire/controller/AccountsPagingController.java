package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.service.AccountPagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import javax.management.openmbean.TabularDataSupport;

@RestController
@RequestMapping("accounts/paging")
@RequiredArgsConstructor
public class AccountsPagingController {

    private final AccountPagingService service;
    private Pages pages;

    @PostMapping("pages")
    public long buildPages(@RequestBody PagingRequest pagingRequest) {
        this.pages = service.constructPages(pagingRequest);
        return pages != null ? (pages.keys()!= null ? pages.keys().size() : 0) : 0;
    }
    @GetMapping("/page/{pageIndex}")
    public Iterable<Account> getPage(@PathVariable int pageIndex) {
        if(pages == null)
            return  null;

        var pagesOfKeys = pages.keys();
        if(pagesOfKeys == null || pagesOfKeys.isEmpty() || pageIndex >= pagesOfKeys.size())
            return null;

        var pageKeys = pagesOfKeys.get(pageIndex);
        return service.getAccounts(pageKeys);
    }

    Pages getPages() {
        return pages;
    }


    @GetMapping("/page/count")
    public int getPageCount() {
        return pages.keys().size();
    }
}
