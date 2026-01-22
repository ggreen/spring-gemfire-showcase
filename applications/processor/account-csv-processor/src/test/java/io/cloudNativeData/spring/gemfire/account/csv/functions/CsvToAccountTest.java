package io.cloudNativeData.spring.gemfire.account.csv.functions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvToAccountTest {

    @Test
    void apply() {
        List<String> csv = List.of("1233","Josiah Account");

        var subject = new CsvToAccount();
        var actual = subject.apply(csv);

        assertThat(actual).isNotNull();

        assertThat(actual.getId()).isEqualTo("1233");
        assertThat(actual.getName()).isEqualTo("Josiah Account");
    }
}