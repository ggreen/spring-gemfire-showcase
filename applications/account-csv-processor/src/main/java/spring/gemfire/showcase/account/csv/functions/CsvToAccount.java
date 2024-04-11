package spring.gemfire.showcase.account.csv.functions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.List;
import java.util.function.Function;

@Component
@Slf4j
public class CsvToAccount implements Function<List<String>, Account> {
    @Override
    public Account apply(List<String> csv) {
        log.info("Account CSV: {}",csv);
        return Account.builder()
                .id(csv.get(0))
                        .name(csv.get(1)).build();
    }
}
