package spring.gemfire.showcase.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;

import java.util.List;
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
    public Account findById(@PathVariable String id)
    {
        return accountRepository.findById(id).orElse(null);
    }

    @DeleteMapping("accounts/{id}")
    public void deleteById(@PathVariable String id)
    {
        accountRepository.deleteById(id);
    }


    @GetMapping("accounts/names/{name}")
    public List<Account> findByName(@PathVariable String name) {
        return accountRepository.findByNameContaining(name);
    }
}
