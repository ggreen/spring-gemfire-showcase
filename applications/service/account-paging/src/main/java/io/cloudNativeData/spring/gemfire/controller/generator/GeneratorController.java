package io.cloudNativeData.spring.gemfire.controller.generator;

import io.cloudNativeData.spring.gemfire.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate accounts to save to GemFire
 * @author gregory green
 */
@RestController
@RequestMapping("generator")
@RequiredArgsConstructor
@Slf4j
public class GeneratorController {

    private final AccountRepository repository;
    private final JavaBeanGeneratorCreator<Account> creator = JavaBeanGeneratorCreator.of(Account.class);

    @PutMapping("account/{count}/{batchSize}")
    public int generatorAccounts(@PathVariable int count, @PathVariable int batchSize) {

        int i = 0;
        List<Account> batch = new ArrayList<>(batchSize);
        Account account;
        for (i = 0; i < count; i++) {
            account = creator.create();
            account.setId(String.valueOf(i));
            batch.add(account);

            if(batch.size() >= batchSize) {
                log.info("Saving {} accounts",batch.size());
                repository.saveAll(batch);
                batch.clear();
            }
        }

        if(!batch.isEmpty()){
            log.info("Saving final {} accounts",batch.size());
            repository.saveAll(batch);
            batch.clear();
        }

        return i;
    }
}
