package spring.gemfire.showcase.account.controller;

import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class AccountController
{
    private final AccountRepository accountRepository;

    @PostMapping("accounts")
    public <S extends Account> S save(@RequestBody S account)
    {
        return accountRepository.save(account);
    }

    @GetMapping("accounts/{id}")
    public Optional<Account> findById(@PathVariable String id)
    {
        return accountRepository.findById(id);
    }

    @DeleteMapping("accounts/{id}")
    public void deleteById(@PathVariable String id)
    {
        accountRepository.deleteById(id);
    }
}
