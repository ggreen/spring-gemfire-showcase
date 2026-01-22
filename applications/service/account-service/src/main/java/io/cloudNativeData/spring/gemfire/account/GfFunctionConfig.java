package io.cloudNativeData.spring.gemfire.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.geode.boot.autoconfigure.condition.ConditionalOnMissingProperty;
import io.cloudNativeData.spring.gemfire.account.function.AccountNameToUpperCase;
import io.cloudNativeData.spring.gemfire.account.repostories.AccountRepository;

@Configuration

public class GfFunctionConfig {

    @ConditionalOnMissingProperty(name = "useGemFireFunction")
    @Bean
    AccountNameToUpperCase accountNameToUpperCase(AccountRepository accountRepository) {
            return accountId -> {
            var account = accountRepository.findById(accountId).orElse(null);
            if (account == null) {return null;}

             account.setName(accountId.toUpperCase());
            return account;
        };
    }
}
