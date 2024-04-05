package spring.gemfire.showcase.account.server.listeners;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.pdx.PdxInstance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAsyncEventListenerTest {

    private AccountAsyncEventListener subject;
    @Mock
    private PdxInstance pdx;

    @Mock
    PreparedStatement preparedStatement;


    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    private Account account = JavaBeanGeneratorCreator.of(Account.class).create();
    @Mock
    private AsyncEvent event;

    private List<AsyncEvent> events;

    @BeforeEach
    void setUp() {

        events = asList(event);
        subject = new AccountAsyncEventListener(dataSource);
    }


    @Test
    void processEvents() throws SQLException {

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(event.getDeserializedValue()).thenReturn(pdx);

        subject.processEvents(events);
        verify(preparedStatement).executeBatch();

    }
}