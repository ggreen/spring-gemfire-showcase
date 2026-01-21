package io.cloudNativeData.spring.gemfire.controller;

import io.cloudNativeData.spring.gemfire.controller.generator.GeneratorController;
import io.cloudNativeData.spring.gemfire.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class GeneratorControllerTest {

    private final static int count = 10;
    private GeneratorController subject;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        subject = new GeneratorController(repository);
    }

    @Test
    void generator() {
        var accountCount = subject.generatorAccounts(count);

        assertThat(accountCount).isEqualTo(count);
    }
}