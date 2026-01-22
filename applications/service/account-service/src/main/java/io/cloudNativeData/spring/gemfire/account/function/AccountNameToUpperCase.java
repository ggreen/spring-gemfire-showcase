package io.cloudNativeData.spring.gemfire.account.function;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

//@OnRegion(region = "Account")
public interface AccountNameToUpperCase {

    @FunctionId("AccountNameToUpperCase")
    Account toUpperCaseName(String accountId);
}
