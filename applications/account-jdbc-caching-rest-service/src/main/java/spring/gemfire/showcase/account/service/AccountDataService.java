package spring.gemfire.showcase.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountDataService implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findByAccountId(String id) {
        var optional = accountRepository.findById(id);
        if (optional.isEmpty())
            return null;

        return optional.get();
    }
}
