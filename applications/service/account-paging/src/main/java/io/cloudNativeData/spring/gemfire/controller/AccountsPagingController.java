package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.service.AccountPagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import javax.management.openmbean.TabularDataSupport;

/**
 * Provides an HTTP interface for the AccountService
 * @author gregory green
 */
@RestController
@RequestMapping("accounts/paging")
@RequiredArgsConstructor
public class AccountsPagingController {

    private final AccountPagingService service;
    private Pages pages;

    /**
     * Build the pages. Note that this saves the pages in memory for a given region.
     * You can scale this strategy by save pages in another GemFire region if needed.
     * @param pagingRequest contains the page size to create
     * @return the number of pages
     */
    @PostMapping("pages")
    public long buildPages(@RequestBody PagingRequest pagingRequest) {
        this.pages = service.constructPages(pagingRequest);
        return pages != null ? (pages.keys()!= null ? pages.keys().size() : 0) : 0;
    }

    /**
     * Get accounts for a given page
     * @param pageIndex the page index started at 0 to retrieve
     * @return the match accounts
     */
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


    /**
     * Get the constructed count of pages
     * @return the number of pages
     */
    @GetMapping("/page/count")
    public int getPageCount() {
        return pages.keys().size();
    }

    /**
     * Note this method will return null if no pages are constructed
     * @return the constructed pages
     */
    Pages getPages() {
        return pages;
    }


}
