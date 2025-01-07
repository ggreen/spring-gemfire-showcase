package spring.gemfire.showcase.account.server.function;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.execute.ResultSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountNameToUpperCaseFunctionTest {

    private AccountNameToUpperCaseFunction subject;
    @Mock
    private RegionFunctionContext rfc;

    @Mock
    private Account account;
    @Mock
    private Region<String, Account> region;
    private String name = "Test";
    @Mock
    private ResultSender<Object> rs;

    @BeforeEach
    void setUp() {
        subject = new AccountNameToUpperCaseFunction();
    }

    @Test
    void toUpper() {

        String[] args = new String[]{account.getId()};

        when(rfc.getDataSet()).thenReturn((Region)region);
        when(rfc.getArguments()).thenReturn(args);
        when(region.get(any())).thenReturn(account);
        when(account.getName()).thenReturn(name);
        when(rfc.getResultSender()).thenReturn(rs);

        subject.execute(rfc);

        verify(account).setName(anyString());
    }
}