package spring.gemfire.showcase.web.session.listener;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.SerializedCacheValue;
import org.apache.geode.pdx.PdxInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.web.session.domain.UserSessionRecord;
import spring.gemfire.showcase.web.session.service.UserSessionReportService;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpringWebSessionListenerTest {

    private SpringWebSessionListener subject;

    @Mock
    private EntryEvent<String, PdxInstance> event;

    @Mock
    private UserSessionReportService repository;

    private UserSessionRecord userSessionRecord = JavaBeanGeneratorCreator.of(UserSessionRecord.class)
            .create();

    @Mock
    private SerializedCacheValue<PdxInstance> serializeNewValue;

    @Mock
    private PdxInstance pdxInstance;
    private String sessionId = "test";


    @BeforeEach
    void setUp() {
        subject = new SpringWebSessionListener(repository);
    }

    @Test
    @DisplayName("Given a event when afterCreate Then rpeort to service")
    void storeUserSessionOnCreate() {
        when(event.getSerializedNewValue()).thenReturn(serializeNewValue);
        when(serializeNewValue.getDeserializedValue()).thenReturn(pdxInstance);
        when(pdxInstance.getField(anyString())).thenReturn(sessionId)
                        .thenReturn(Instant.now());

        subject.afterCreate(event);

        verify(repository).reportCreate(any(UserSessionRecord.class));

    }

    @Test
    void storeUserSessionOnCreateNull() {

       assertDoesNotThrow( () -> subject.afterCreate(event));


    }

    @Test
    void storeUserSessionOnCreateOnlyserializeNewValueAndPdx() {
        when(event.getSerializedNewValue()).thenReturn(serializeNewValue);
        when(serializeNewValue.getDeserializedValue()).thenReturn(pdxInstance);

        assertDoesNotThrow( () -> subject.afterCreate(event));

    }

    @Test
    void storeUserSessionOnCreateOnlyserializeNewValueNoOPdx() {
        when(event.getSerializedNewValue()).thenReturn(serializeNewValue);

        assertDoesNotThrow( () -> subject.afterCreate(event));

    }




    @Test
    void destroy() {

        when(event.getKey()).thenReturn(sessionId);

        subject.afterDestroy(event);

        verify(repository).reportDestroyedSession(anyString());
    }
}