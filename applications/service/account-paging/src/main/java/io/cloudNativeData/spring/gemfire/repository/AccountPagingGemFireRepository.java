package io.cloudNativeData.spring.gemfire.repository;

import lombok.RequiredArgsConstructor;
import org.apache.geode.cache.Region;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.spring.gemfire.account.domain.account.Account;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class AccountPagingGemFireRepository implements AccountPagingRepository {

    private final Region<String,Account> gemfireTemplate;

    @Override
    public Set<String> findAllKeys() {
        return gemfireTemplate.keySetOnServer();
    }

    @Override
    public Iterable<Account> findAll(Collection<String> keys) {
        Map<String,Account> results = gemfireTemplate.getAll(keys);
        return results.values();
    }
}
