package spring.gemfire.showcase.account.server.listeners;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.jdbc.dataSource.ConnectionCreator;
import nyla.solutions.core.patterns.jdbc.dataSource.ConnectionDataSource;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.logging.internal.log4j.LogWriterLogger;
import org.apache.geode.pdx.PdxInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spring.gemfire.showcase.account.domain.account.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static java.lang.String.valueOf;

public class AccountAsyncEventListener implements AsyncEventListener {

    private final DataSource dataSource;

    private String saveAccountSql = """
            INSERT INTO account (id, name)
            VALUES (?,?)
            ON CONFLICT(id)
            DO UPDATE SET name = ?
            """;
    private Logger log = LogManager.getLogger(AccountAsyncEventListener.class);

    public AccountAsyncEventListener(DataSource dataSource)
    {
        this.dataSource  = dataSource;
    }
    public AccountAsyncEventListener()
    {
        log.info("CREATED {}",this);
        this.dataSource  = new ConnectionDataSource();
    }

    @SneakyThrows
    @Override
    public boolean processEvents(List<AsyncEvent> events) {

        try {

            log.info("events {}",events);

            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(saveAccountSql)) {

                    for (AsyncEvent event : events) {
                        PdxInstance account = (PdxInstance) event.getDeserializedValue();

                        preparedStatement.setString(1, valueOf(account.getField("id")));
                        preparedStatement.setString(2, valueOf(account.getField("name")));
                        preparedStatement.setString(3, valueOf(account.getField("name")));
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("EXCEPTION {}", e);
            //return false to reprocess
        }
        return true;
    }


}
