package io.cloudNativeData.spring.gemfire.account.repository.fallback;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepositoryFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Fallback implementation of the {@link AccountRepositoryFallback} interface that interacts
 * with a secondary VMware GemFire (formerly Geode) cache region.
 * <p>
 * This repository is typically used as a failover or secondary data source when the primary
 * repository cluster is unavailable or for specific regional data routing. It utilizes a
 * designated {@link GemfireTemplate} configured for a secondary cluster alias.
 * </p>
 * * @author Gregory Green
 */

@Repository
public class GemFireRegionAliasAccountRepositoryFallback implements AccountRepositoryFallback {

    private final GemfireTemplate gemfireTemplate;

    public GemFireRegionAliasAccountRepositoryFallback(@Qualifier("accountTemplateCluster2")
                                                       GemfireTemplate gemfireTemplate) {
        this.gemfireTemplate = gemfireTemplate;
    }

    /**
     * Retrieves an {@link Account} by its unique identifier from the fallback GemFire region.
     *
     * @param id the unique identifier of the account to look up; must not be {@code null}.
     * @return an {@link Optional} containing the found {@code Account}, or {@link Optional#empty()}
     * if no account exists with the given ID.
     */
    @Override
    public Optional<Account> findById(String id) {
        Account account = gemfireTemplate.get(id);

        return account != null ? Optional.of(account) : Optional.empty();
    }

    /**
     * Saves or updates the given {@link Account} in the fallback GemFire region.
     *
     * @param account the {@code Account} entity to persist; must not be {@code null}.
     * @return the saved {@code Account} instance as returned by the underlying GemFire cache operation.
     */
    @Override
    public Account save(Account account) {
        return gemfireTemplate.put(account.getId(), account);
    }
}
