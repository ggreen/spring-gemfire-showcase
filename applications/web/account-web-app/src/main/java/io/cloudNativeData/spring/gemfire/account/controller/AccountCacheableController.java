package io.cloudNativeData.spring.gemfire.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

@RestController
@RequestMapping("cacheable/account")
@Slf4j
public class AccountCacheableController {

    @Cacheable(value = "FavoriteAccounts",key = "#id")
    @GetMapping("favorites")
    public Account favoriteAccount(String id,String name)
    {
        log.info("Building RESPONSE for id: {}, name: {}",id,name
        );

        return Account.builder().id(id).name(name).build();
    }


}
