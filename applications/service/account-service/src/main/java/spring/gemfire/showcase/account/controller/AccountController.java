package spring.gemfire.showcase.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.function.AccountNameToUpperCase;
import spring.gemfire.showcase.account.repostories.AccountRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("accounts")
public class AccountController
{
    private final AccountRepository accountRepository;
    private final AccountNameToUpperCase accountNameToUpperCase;

    @PostMapping
    public <S extends Account> S save(@RequestBody S account)
    {
        return accountRepository.save(account);
    }

    @GetMapping("{id}")
    public Account findById(@PathVariable String id)
    {
        return accountRepository.findById(id).orElse(null);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id)
    {
        accountRepository.deleteById(id);
    }


    @GetMapping("names/{name}")
    public List<Account> findByName(@PathVariable String name) {
        return accountRepository.findByNameContaining(name);
    }

    @GetMapping("paging/{pageNumber}/{pageSize}")
    public List<Account> findAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
        return accountRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }


    @PostMapping("name/like")
    public List<Account> findFirst2ByNameLikeOrderByByName(@RequestBody String nameLike){
        ScrollPosition offset = ScrollPosition.keyset();
        return accountRepository.findFirst2ByNameLikeOrderByName(nameLike, offset).getContent();
    }

    @PutMapping("functions/upperCase/name/{accountId}")
    public Account toUpperCaseName(@PathVariable String accountId) {
        return this.accountNameToUpperCase.toUpperCaseName(accountId);
    }
}
