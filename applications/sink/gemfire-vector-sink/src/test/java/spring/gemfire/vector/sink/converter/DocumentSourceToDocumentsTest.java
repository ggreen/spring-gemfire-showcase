package spring.gemfire.vector.sink.converter;

import lombok.SneakyThrows;
import nyla.solutions.core.util.Debugger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for DocumentSourceToDocuments
 * @author Gregory Green
 */
@ExtendWith(MockitoExtension.class)
class DocumentSourceToDocumentsTest {

    private final String documentSource = "This is a sample document content.";

    private DocumentSourceToDocuments subject;
    private int contentMaxLengthBatchSize = 5000;

    @BeforeEach
    void setUp() {
        subject = new DocumentSourceToDocuments(contentMaxLengthBatchSize);
    }

    @Test
    void convert() {

        var actual = subject.convert(documentSource);

        assertNotNull(actual);

    }

    @SneakyThrows
    @Test
    void objectMapperUrls() {
        var url = "http://therevelationsquad.com/";

        Debugger.println(url);
        var actual = subject.convert(url);
        assertNotNull(actual);

        actual = subject.convert("https://github.com/ggreen?fg_force_rendering_mode=Images&fireglass_rsn=true#fireglass_params&tabid=a2c8ae713fb7ae4e&start_with_session_counter=2&application_server_address=isolation-3-us-east4.prod.fire.glass");
        assertNotNull(actual);
    }

    @Test
    void splitGideonDoc() {

        var url = "https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/";
        var actual = subject.convert(url);
        for(var doc : actual) {
            System.out.println(doc.getText().length());

            assertThat(doc.getText().length()).isLessThanOrEqualTo(contentMaxLengthBatchSize);
        }
    }
}