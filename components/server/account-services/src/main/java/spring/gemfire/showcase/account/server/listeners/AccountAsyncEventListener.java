package spring.gemfire.showcase.account.server.listeners;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;

import java.util.List;

@Slf4j
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
