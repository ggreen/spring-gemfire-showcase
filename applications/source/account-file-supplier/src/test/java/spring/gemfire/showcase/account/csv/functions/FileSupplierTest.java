package spring.gemfire.showcase.account.csv.functions;

import lombok.SneakyThrows;
import nyla.solutions.core.io.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cased for FileSupplierTest
 */
class FileSupplierTest {

    private File file = new File("src/test/resources/account.csv");
    private FileSupplier subject;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        IO.dir().delete(file);
    }

    @SneakyThrows
    @Test
    void get() {

        subject = new FileSupplier(file.toPath().getParent().toFile().getAbsolutePath(),100);

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                var line1  ="""
                        "1","Record"                       
                        """;
                IO.writer().writeAppend(file,line1);

                Thread.sleep(5000);
                var line2  ="""
                        "2","Record"                       
                        """;
                IO.writer().writeAppend(file,line2);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        var line1 = subject.get();
        assertThat(line1).contains("Record");

        var line2 = subject.get();
        assertThat(line1).contains("Record");

    }
}