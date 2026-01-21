package io.cloudNativeData.spring.gemfire.repository;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountPagingGemFireRepositoryTest {

    private AccountPagingGemFireRepository subject;
    @Mock
    private Region<String,Account> region;

    private final Account account = JavaBeanGeneratorCreator.of(Account.class).create();

    @BeforeEach
    void setUp() {
        subject = new AccountPagingGemFireRepository(region);
    }

    @Test
    void findAllKeys() {
        Set<String> expected = Set.of("k1");
        when(region.keySetOnServer()).thenReturn(expected);
        var actual = subject.findAllKeys();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findAll() {

        when(region.getAll(any())).thenReturn(Map.of("key1",account));
        var expected = List.of(account);
        Collection<String> keys = List.of("key1", "key2", "key3");

        var actual = subject.findAll(keys);
        assertThat(actual.iterator().next()).isEqualTo(expected.iterator().next());


    }
}