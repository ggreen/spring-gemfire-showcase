package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.controller.generator.GeneratorController;
import io.cloudNativeData.spring.gemfire.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class GeneratorControllerTest {

    private final static int count = 10;
    private GeneratorController subject;

    @Mock
    private AccountRepository repository;
    private int batchSize = 3;

    @BeforeEach
    void setUp() {
        subject = new GeneratorController(repository);
    }

    @Test
    void generator() {
        var accountCount = subject.generatorAccounts(count,batchSize);

        assertThat(accountCount).isEqualTo(count);
        verify(repository,times(4)).saveAll(any());
    }
}