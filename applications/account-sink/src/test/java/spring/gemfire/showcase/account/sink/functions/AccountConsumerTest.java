package spring.gemfire.showcase.account.sink.functions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.sink.repostories.AccountRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountConsumerTest {

    private Account account = JavaBeanGeneratorCreator.of(Account.class).create();
    private AccountConsumer subject;
    @Mock
    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        subject = new AccountConsumer(repository);
    }

    @Test
    void accept() {
        subject.accept(account);

        verify(repository).save(any());
    }
}