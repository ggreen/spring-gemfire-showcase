package io.cloudNativeData.spring.gemfire.account.repository.fallback;

import io.cloudNativeData.spring.gemfire.account.domain.account.Account;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.gemfire.GemfireTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GemFireRegionAliasAccountRepositoryFallbackTest {

    @Mock
    private GemfireTemplate gemfireTemplate;

    private final Account account = JavaBeanGeneratorCreator.of(Account.class).create();
    private GemFireRegionAliasAccountRepositoryFallback subject;

    @BeforeEach
    void setUp() {
        subject = new GemFireRegionAliasAccountRepositoryFallback(gemfireTemplate);
    }

    @Test
    void findById() {

        when(gemfireTemplate.get(anyString())).thenReturn(account);

        var actual = subject.findById(account.getId());

        assertThat(actual).isEqualTo(Optional.of(account));
    }

    @Test
    void save() {

        subject.save(account);

        verify(gemfireTemplate).put(any(),any());
    }
}