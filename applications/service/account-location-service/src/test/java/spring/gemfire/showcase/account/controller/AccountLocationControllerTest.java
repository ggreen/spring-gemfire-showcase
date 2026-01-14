package spring.gemfire.showcase.account.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;
import spring.gemfire.showcase.account.domain.account.AccountLocation;
import spring.gemfire.showcase.account.domain.account.Location;
import spring.gemfire.showcase.account.repository.AccountRepository;
import spring.gemfire.showcase.account.repository.LocationRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountLocationControllertTest {

    private final static String invalidZipCode = "Invalid";
    private final static String validZipCode = "09999";

    private AccountLocationController subject;
    private final AccountLocation accountLocation = JavaBeanGeneratorCreator.of(AccountLocation.class).create();
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        subject = new AccountLocationController(accountRepository,
                locationRepository);
    }

    @Test
    void save() {

        accountLocation.getLocation().setZipCode(validZipCode);
        subject.save(accountLocation);

        verify(accountRepository).save(any(Account.class));
        verify(locationRepository).save(any(Location.class));
    }

    @Test
    void rollback() {

        accountLocation.getLocation().setZipCode(invalidZipCode);
        assertThrows(IllegalArgumentException.class, () -> subject.save(accountLocation));
        verify(accountRepository).save(any(Account.class));
        verify(locationRepository, never()).save(any(Location.class));
    }
}