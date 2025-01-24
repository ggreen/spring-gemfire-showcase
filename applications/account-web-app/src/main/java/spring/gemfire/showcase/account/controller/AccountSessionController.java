package spring.gemfire.showcase.account.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.domain.account.Account;

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
