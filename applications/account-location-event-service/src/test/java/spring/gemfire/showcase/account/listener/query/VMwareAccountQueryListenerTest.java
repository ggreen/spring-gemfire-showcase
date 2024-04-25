package spring.gemfire.showcase.account.listener.query;

import org.apache.geode.cache.query.CqEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.gemfire.GemfireTemplate;
import spring.gemfire.showcase.account.domain.account.Location;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VMwareAccountQueryListenerTest {


    @Mock
    private GemfireTemplate locationGemFireTemplate;

    @Mock
    private CqEvent cqEvent;

    private VMwareAccountQueryListener subject;

    @BeforeEach
    void setUp() {
        subject = new VMwareAccountQueryListener(locationGemFireTemplate);
    }

    @Test
    void addVMwareLocation() {
        var expectedKey = "1";
        when(cqEvent.getKey()).thenReturn(expectedKey);

        subject.addVMwareLocation(cqEvent);

        verify(locationGemFireTemplate).put(anyString(), Mockito.any(Location.class));

    }

}