package spring.gemfire.showcase.account.listener.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import spring.gemfire.showcase.account.domain.account.Account;

@Slf4j
public class VMwareAccountCacheListener extends CacheListenerAdapter<String, Account> {

    public void afterCreate(EntryEvent<String, Account> event) {
        processEvent(event);
    }

    public void  afterUpdate(EntryEvent<String, Account> event) {
        processEvent(event);
    }

    private void processEvent(EntryEvent<String, Account> event)
    {
        log.info("*********************** KEY {} EVENT {}}",event.getKey(),event.getNewValue());
    }
}
