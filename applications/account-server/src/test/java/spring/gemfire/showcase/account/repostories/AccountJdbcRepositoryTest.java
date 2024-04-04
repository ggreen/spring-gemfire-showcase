package spring.gemfire.showcase.account.repostories;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import spring.gemfire.showcase.account.entity.Account;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountJdbcRepositoryTest {

    private AccountJdbcRepository subject;
    @Mock
    private JdbcTemplate jdbcTemplate;
    private Account account = JavaBeanGeneratorCreator.of(Account.class).create();

    @BeforeEach
    void setUp() {
        subject = new AccountJdbcRepository(jdbcTemplate);
    }

    @Test
    void save() {

        var actual = subject.save(account);
        verify(jdbcTemplate, atLeastOnce()).update(anyString(),any(PreparedStatementSetter.class));
        assertEquals(account,actual);
    }

    @Test
    void findById() {


        when(jdbcTemplate.
        queryForObject(
                anyString(),
                (RowMapper<Object>) Mockito.any(),
                anyString())).thenReturn(account);

        assertEquals(Optional.of(account),subject.findById(account.getId()));
    }

}