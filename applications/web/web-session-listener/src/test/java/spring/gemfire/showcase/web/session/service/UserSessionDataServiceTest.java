package spring.gemfire.showcase.web.session.service;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.web.session.domain.UserSession;
import spring.gemfire.showcase.web.session.domain.UserSessionStartEvent;
import spring.gemfire.showcase.web.session.repository.UserSessionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSessionDataServiceTest {


    private UserSessionDataService subject;


    @Mock
    private UserSessionRepository repository;

    private UserSessionStartEvent startEvent;

    private UserSession userSession = JavaBeanGeneratorCreator.of(UserSession.class)
            .create();
    private String sessionId = "test";
    private String userName = "user";



    @BeforeEach
    void setUp() {
        startEvent = JavaBeanGeneratorCreator.of(UserSessionStartEvent.class)
                .create();
        subject = new UserSessionDataService(repository);
    }

    @Test
    void storeUserSessionOnCreate() {

        subject.saveStart(startEvent);

        verify(repository).save(any());

    }

    @Test
    void doNotSaveWhenUserIdIsBlank() {

        startEvent.setUserId(null);
        subject.saveStart(startEvent);

        verify(repository,never()).save(any());

    }


    @Test
    void destroy_noExceptions() {


        assertDoesNotThrow(() -> subject.saveEnd(null));

    }


    @Test
    void saveEnd() {

        when(repository.findById(anyString())).thenReturn(Optional.of(userSession));
        subject.saveEnd(startEvent.getSessionId());

        verify(repository).save(any());
    }
}
