package spring.gemfire.showcase.account.function;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;
import spring.gemfire.showcase.account.domain.account.Account;

@OnRegion(region = "Account")
public interface AccountNameToUpperCase {

    @FunctionId("AccountNameToUpperCase")
    Account toUpperCaseName(String accountId);
}
