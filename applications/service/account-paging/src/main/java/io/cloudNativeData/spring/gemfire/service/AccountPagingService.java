package io.cloudNativeData.spring.gemfire.service;

import io.cloudNativeData.spring.gemfire.domain.PagingRequest;
import io.cloudNativeData.spring.gemfire.domain.Pages;
import io.cloudNativeData.spring.gemfire.repository.AccountPagingRepository;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.util.Organizer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountPagingService {

    private final AccountPagingRepository pagingRepository;

    public Pages constructPages(@RequestBody PagingRequest pagingRequest) {

        var keys = pagingRepository.findAllKeys();

        var keyPages = Organizer.change().toPages(keys, pagingRequest.pageSize());

        return Pages.builder().keys(keyPages).build();
    }

    public Iterable<Account> getAccounts(@RequestBody Collection<String> keys) {
        return pagingRepository.findAll(keys);
    }
}
