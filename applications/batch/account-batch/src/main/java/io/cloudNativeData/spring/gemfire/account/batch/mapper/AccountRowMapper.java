package io.cloudNativeData.spring.gemfire.account.batch.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.domain.account.Contact;
import io.cloudNativeData.spring.gemfire.account.domain.account.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        //account
        var accountBuilder = Account.builder();
        accountBuilder.id(rs.getString("id"));
        accountBuilder.name(rs.getString("name"));

        //customer
        var customerBuilder = Customer.builder();
        customerBuilder.firstName(rs.getString("first_nm"));
        customerBuilder.lastName(rs.getString("last_nm"));

        //contact
        var contactBuilder = Contact.builder();
        contactBuilder.email(rs.getString("email"));
        contactBuilder.phone(rs.getString("phone"));
        customerBuilder.contact(contactBuilder.build());

        return accountBuilder.customer(customerBuilder.build()).build();
    }
}
