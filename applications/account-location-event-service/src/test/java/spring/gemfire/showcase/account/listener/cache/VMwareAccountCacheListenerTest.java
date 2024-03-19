package spring.gemfire.showcase.account.listener.cache;

import spring.gemfire.showcase.account.domain.account.Account;
import org.apache.geode.cache.EntryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VMwareAccountCacheListenerTest {

    @Mock
    private EntryEvent<String, Account> event;
    private VMwareAccountCacheListener subject;

    @BeforeEach
    void setUp() {
        subject = new VMwareAccountCacheListener();
    }

    @Test
    void afterCreate() {
        subject.afterCreate(event);

        verify(event).getKey();
    }

    @Test
    void afterUpdate() {

        subject.afterUpdate(event);

        verify(event).getKey();
    }
}