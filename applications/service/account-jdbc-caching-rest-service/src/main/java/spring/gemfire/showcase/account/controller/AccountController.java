package spring.gemfire.showcase.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.service.AccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping("accounts")
public class AccountController
{
    private final AccountService accountService;

    @PostMapping
    void save(@RequestBody Account account) {
        accountService.save(account);
    }

    @GetMapping("{id}")
    ResponseEntity<Account> findByAccountId(@PathVariable("id") String id) {
    var account = accountService.findByAccountId(id);

        if(account == null)
            return ResponseEntity.notFound().build();

    return ResponseEntity.ok(account);
}
}
