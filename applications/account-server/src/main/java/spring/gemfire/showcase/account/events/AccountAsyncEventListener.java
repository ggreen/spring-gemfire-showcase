package spring.gemfire.showcase.account.events;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AccountAsyncEventListener implements AsyncEventListener {

    public AccountAsyncEventListener()
    {
        log.info("CREATED");
    }

    @Override
    public boolean processEvents(List<AsyncEvent> list) {

        log.info("events: {}",list);
        return true;
    }
}
