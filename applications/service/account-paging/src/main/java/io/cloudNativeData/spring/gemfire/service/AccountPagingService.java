package io.cloudNativeData.spring.gemfire.service;

import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.repository.AccountPagingRepository;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.util.Organizer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.Collection;

/**
 * Core service four paging the account records
 * @author Gregory Green
 */
@Service
@RequiredArgsConstructor
public class AccountPagingService {

    private final AccountPagingRepository pagingRepository;

    /**
     * Construct pages for the account repositoru
     * @param pagingRequest the page request contains the page sizes
     * @return the pages of region keys
     */
    public Pages constructPages(@RequestBody PagingRequest pagingRequest) {

        var keys = pagingRepository.findAllKeys();

        var keyPages = Organizer.change().toPages(keys, pagingRequest.pageSize());

        return Pages.builder().keys(keyPages).build();
    }

    /**
     * Get accounts for a given page of keys
     * @param keys get all the account for the given keys
     * @return the iterator of account with the provided keys
     */
    public Iterable<Account> getAccounts(@RequestBody Collection<String> keys) {
        return pagingRepository.findAll(keys);
    }
}
