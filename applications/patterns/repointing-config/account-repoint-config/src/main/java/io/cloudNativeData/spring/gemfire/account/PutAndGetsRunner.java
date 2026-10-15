package io.cloudNativeData.spring.gemfire.account;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Runner a quality check for the circuit breaker.
 * @author gregory green
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PutAndGetsRunner implements ApplicationRunner {
    private final AccountService accountService;
    @Value("${app.sleepMs}")
    private long sleepMs;

    @Value("${app.accountCount:20}")
    private int accountCount;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        var accounts = JavaBeanGeneratorCreator.of(Account.class).createCollection(accountCount);

        for (Account account : accounts) {
            accountService.saveAccount(account);
        }

        var count = 1;

        while (true) {
            var start = System.currentTimeMillis();
            for (Account account : accounts) {


                var foundAccount = accountService.findAccountById(account.getId());

                foundAccount.setName(account.getId() + count++);
                accountService.saveAccount(foundAccount);

            }
            var end = System.currentTimeMillis();
            log.info("{} accounts in {} tps", accounts.size(), ((end - start) / accountCount) / 1000);

            Thread.sleep(sleepMs);
        }
    }
}
