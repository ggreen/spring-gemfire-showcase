package io.cloudNativeData.spring.gemfire.account.server.function;


import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.pdx.PdxInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import static java.lang.String.valueOf;

/**
 * Example GemFire Function
 * @author gregory green
 */
public class AccountNameToUpperCaseFunction implements Function<Object[]> {

            private Logger logger = LogManager.getLogger(AccountNameToUpperCaseFunction.class);


            @Override
            public void execute(FunctionContext<Object[]> functionContext) {

                var rfc = (RegionFunctionContext<Object[]>)functionContext ;
                Region<String,Object> region =  rfc.getDataSet();

                var accountId = valueOf(functionContext.getArguments()[0]);
                var accountObject = region.get(accountId);

                if(accountObject  == null)
                    return;

                Account account;

                if(accountObject instanceof PdxInstance accountPdx)
                    account = (Account)accountPdx.getObject();
                else
                    account = (Account)accountObject;

                logger.info("account {}",account);

                account.setName(account.getName().toUpperCase());

                functionContext.getResultSender().lastResult(account);

    }

    @Override
    public String getId() {
        return "AccountNameToUpperCase";
    }
}
