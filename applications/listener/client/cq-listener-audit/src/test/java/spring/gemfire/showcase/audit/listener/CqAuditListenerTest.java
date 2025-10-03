package spring.gemfire.showcase.audit.listener;

import org.apache.geode.cache.query.CqEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CqAuditListenerTest {

    private CqAuditListener subject;
    @Mock
    private CqEvent cqEvent;

    @BeforeEach
    void setUp() {
        subject = new CqAuditListener();
    }

    @Test
    void audit() {
        subject.onEvent(cqEvent);


        verify(cqEvent).getNewValue();
    }
}