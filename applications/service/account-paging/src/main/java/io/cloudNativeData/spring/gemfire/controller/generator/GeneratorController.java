package io.cloudNativeData.spring.gemfire.controller.generator;

import io.cloudNativeData.spring.gemfire.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gemfire.showcase.account.domain.account.Account;

@RestController
@RequestMapping("generator")
@RequiredArgsConstructor
@Slf4j
public class GeneratorController {

    private final AccountRepository repository;
    private final JavaBeanGeneratorCreator<Account> creator = JavaBeanGeneratorCreator.of(Account.class);

    @PutMapping("account")
    public int generatorAccounts(int count) {

        int i = 0;
        Account account;
        for (i = 0; i < count; i++) {
           account = creator.create();
           account.setId(String.valueOf(i));
           log.info("Saving account with id {}", account.getId());

           repository.save(account);
        }
        return i;
    }
}
