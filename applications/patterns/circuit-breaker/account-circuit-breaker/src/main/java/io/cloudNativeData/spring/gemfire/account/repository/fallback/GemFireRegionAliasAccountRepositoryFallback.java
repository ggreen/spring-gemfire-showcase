package io.cloudNativeData.spring.gemfire.account.repository.fallback;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepositoryFallback;
import lombok.RequiredArgsConstructor;
import org.apache.geode.cache.Region;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GemFireRegionAliasAccountRepositoryFallback implements AccountRepositoryFallback {

    private final Region<String, Account> regionCluster2;

    @Override
    public Optional<Account> findById(String id) {
        var account = regionCluster2.get(id);

        return account != null ? Optional.of(account) : Optional.empty();
    }

    @Override
    public Account save(Account account) {
        return regionCluster2.put(account.getId(), account);
    }
}
