package spring.gemfire.showcase.account.sink.functions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.sink.repostories.AccountRepository;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class AccountConsumer implements Consumer<Account> {
    private final AccountRepository repository;

    public void accept(Account account) {
        repository.save(account);
    }
}
