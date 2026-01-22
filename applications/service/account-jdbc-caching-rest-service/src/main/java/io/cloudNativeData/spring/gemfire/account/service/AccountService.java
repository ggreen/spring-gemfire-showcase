package io.cloudNativeData.spring.gemfire.account.service;

import io.cloudNativeData.spring.gemfire.account.entity.Account;

public interface AccountService {

    Account save(Account account);

    Account findByAccountId(String id);
}
