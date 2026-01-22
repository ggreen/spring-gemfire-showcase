package io.cloudNativeData.spring.gemfire.account.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

@RestController
@RequestMapping("session/account")
public class AccountSessionController {

    @PostMapping
    public void putSession(@RequestBody Account account, HttpSession httpSession)
    {
        httpSession.setAttribute(account.getId(),account);
    }

    @GetMapping("{id}")
    public Account retrieveSession(@PathVariable String id, HttpSession httpSession)
    {
        return (Account) httpSession.getAttribute(id);
    }

    @DeleteMapping
    public void invalidateSession(HttpSession httpSession)
    {
        httpSession.invalidate();
    }
}
