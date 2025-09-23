package spring.gemfire.showcase.account.server.account.listeners;

import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.gemfire.showcase.account.server.account.listeners.domain.AccountEntity;
import spring.gemfire.showcase.account.server.account.listeners.repository.AccountRepository;

import java.util.List;

public class AccountAsyncEventListener implements AsyncEventListener {
    public AccountAsyncEventListener()
    {

        System.setProperty("spring.datasource.url","jdbc:postgresql://localhost:5432/postgres");
        System.setProperty("spring.datasource.username","postgres");
        System.setProperty("spring.datasource.driver-class-name","org.postgresql.Driver");


    }
    private final Logger log = LogManager.getLogger(AccountAsyncEventListener.class);

    @Override
    public boolean processEvents(List<AsyncEvent> list) {
        log.info("Hi!!!!!");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        var repository = context.getBean(AccountRepository.class);
        repository.save(new AccountEntity("1","2"));
        return false;
    }
}
