package io.cloudNativeData.spring.gemfire.account;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PutAndGets implements ApplicationRunner {
    private final AccountRepository accountRepository;
    @Value("${app.sleepMs}")
    private long sleepMs;

    @Value("${app.accountCount:20}")
    private int accountCount;

    private final CircuitBreakerFactory cbFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        var accounts = JavaBeanGeneratorCreator.of(Account.class).createCollection(accountCount);

        for (Account account : accounts) {
            accountRepository.save(account);
        }

        var count = 1;

        while (true) {
            var start = System.currentTimeMillis();
            for (Account account : accounts) {


                var foundAccount = cbFactory.create("get")

                        .run(() -> accountRepository.findById(account.getId()).get(),
                                e -> {
                            log.error("GET FALL BACKING error: {}",e);
                            return null;
                                });

                if(foundAccount == null) continue;

                foundAccount.setName(account.getId() + count++);
                cbFactory.create("save").run(() -> {
                    accountRepository.save(foundAccount);
                    return null;
                }, e -> {

                    log.error("SAVE FALL BACKING error: {}",e);
                    return null;
                });

            }
            var end = System.currentTimeMillis();
            log.info("{} accounts in {} tps", accounts.size(), ((end - start) / accountCount) / 1000);

            Thread.sleep(sleepMs);
        }
    }
}
