package io.cloudNativeData.spring.gemfire.account.repostories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.entity.Account;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AccountJdbcRepository implements AccountRepository{

    private final JdbcTemplate jdbcTemplate;

    private static final String updateSql = "update gf_cache.ACCOUNTS set name = ? where id = ?";
    private static final String insertSql = "insert into gf_cache.ACCOUNTS (id,name) values (?,?)";
    private static final String selectSql = "select id as id, name as name from gf_cache.ACCOUNTS where id = ?";

    @Override
    public Account save(Account account) {

        PreparedStatementSetter updateSetter  = ps -> {
            ps.setString(1, account.getName());
            ps.setString(2, account.getId());
        };

        log.info(updateSql);

        var cnt = jdbcTemplate.update(updateSql, updateSetter);


        PreparedStatementSetter insertSetter   = ps ->
                {
            ps.setString(1, account.getId());
            ps.setString(2, account.getName());
        };

        if(cnt == 0)
        {
            log.info(insertSql);
            jdbcTemplate.update(insertSql, insertSetter);
        }

        return account;
    }

    @Override
    public Optional<Account> findById(String id) {
        RowMapper<Account> rowMapper =  (rs, rowIndex) -> {
                return new Account(rs.getString(1), rs.getString(2));
        };

        log.info(selectSql);

        try
        {
            var account  = jdbcTemplate.queryForObject(
                selectSql,
                rowMapper,
                id);

            if(account == null)
                return Optional.empty();

            return Optional.of(account);
        }
        catch(EmptyResultDataAccessException e )
        {
            return Optional.empty();
        }
    }
}
