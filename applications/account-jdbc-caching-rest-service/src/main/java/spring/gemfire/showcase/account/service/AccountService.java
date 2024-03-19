package spring.gemfire.showcase.account.service;

import spring.gemfire.showcase.account.entity.Account;

public interface AccountService {

    Account save(Account account);

    Account findByAccountId(String id);
}
