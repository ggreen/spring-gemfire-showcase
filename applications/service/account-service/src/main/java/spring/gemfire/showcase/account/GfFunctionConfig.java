package spring.gemfire.showcase.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.geode.boot.autoconfigure.condition.ConditionalOnMissingProperty;
import spring.gemfire.showcase.account.function.AccountNameToUpperCase;
import spring.gemfire.showcase.account.repostories.AccountRepository;

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
