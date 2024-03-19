package spring.gemfire.showcase.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import spring.gemfire.showcase.account.entity.Account;
import spring.gemfire.showcase.account.repostories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.service.AccountService;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AccountController
{
    private final AccountService accountService;

    @PostMapping("accounts")
    void save(@RequestBody Account account) {
        accountService.save(account);
    }

    @GetMapping("accounts/{id}")
    ResponseEntity<Account> findByAccountId(@PathVariable("id") String id) {
    var account = accountService.findByAccountId(id);

        if(account == null)
            return ResponseEntity.notFound().build();

    return ResponseEntity.ok(account);
}
}
