package spring.gemfire.showcase.web.session.listener;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.pdx.PdxInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.web.session.domain.UserSession;
import spring.gemfire.showcase.web.session.service.UserSessionService;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringWebSessionListenerTest {

    private SpringWebSessionListener subject;

    @Mock
    private EntryEvent<String, PdxInstance> event;

    @Mock
    private UserSessionService service;

    private UserSession userSession = JavaBeanGeneratorCreator.of(UserSession.class)
            .create();

    @Mock
    private PdxInstance pdxInstance;
    private String sessionId = "test";
    private String userName = "user";


    @BeforeEach
    void setUp() {
        subject = new SpringWebSessionListener(service);
    }

    @Test
    void storeUserSessionOnCreate() {
        when(event.getNewValue()).thenReturn(pdxInstance);
        when(pdxInstance.getField(anyString())).thenReturn(sessionId)
                        .thenReturn(Instant.now());

        subject.afterCreate(event);

        verify(service).saveStart(any());

    }

    @Test
    void storeUserSessionOnCreateNull() {

       assertDoesNotThrow( () -> subject.afterCreate(event));

        verify(service,never()).saveStart(any());


    }

    @Test
    void storeUserSessionSaves() {
        when(event.getNewValue()).thenReturn(pdxInstance);
        when(pdxInstance.getField(anyString())).thenReturn(userName);

        assertDoesNotThrow( () -> subject.afterCreate(event));

        verify(service).saveStart(any());

    }




    @Test
    void destroy_noExceptions() {

        when(event.getKey()).thenReturn(sessionId);

        assertDoesNotThrow( () -> subject.afterDestroy(event));

    }


    @Test
    void destroy() {

        when(event.getKey()).thenReturn(sessionId);
        subject.afterDestroy(event);

        verify(service).saveEnd(any());
    }
}