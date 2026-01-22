package io.cloudNativeData.spring.gemfire.account.sink.functions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.sink.repostories.AccountRepository;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class AccountConsumer implements Consumer<Account> {
    private final AccountRepository repository;

    public void accept(Account account) {
        repository.save(account);
    }
}
