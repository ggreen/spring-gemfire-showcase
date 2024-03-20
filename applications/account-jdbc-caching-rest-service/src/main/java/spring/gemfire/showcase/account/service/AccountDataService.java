package spring.gemfire.showcase.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountDataService implements AccountService{
    private final AccountRepository accountRepository;

    @CacheEvict(value = {"AccountDbCache"}, key = "#account.id")
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Cacheable(value = {"AccountDbCache"})
    @Override
    public Account findByAccountId(String id) {
        log.info("!!!!==============SEARCHING for Account Id:{}",id);
        var optional = accountRepository.findById(id);
        if (optional.isEmpty())
            return null;

        return optional.get();
    }
}
