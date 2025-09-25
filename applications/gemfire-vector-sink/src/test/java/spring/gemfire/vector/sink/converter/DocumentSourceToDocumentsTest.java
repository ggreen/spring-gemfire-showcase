package spring.gemfire.vector.sink.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import nyla.solutions.core.util.Debugger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.vector.sink.domain.DocumentSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for DocumentSourceToDocuments
 * @author Gregory Green
 */
@ExtendWith(MockitoExtension.class)
class DocumentSourceToDocumentsTest {

    private final DocumentSource documentSource = new DocumentSource("This is a sample document content.",
            null);

    private  final ObjectMapper objectMapper = new ObjectMapper();
    private DocumentSourceToDocuments subject;

    @BeforeEach
    void setUp() {
        subject = new DocumentSourceToDocuments();
    }

    @Test
    void convert() {

        var actual = subject.convert(documentSource);

        assertNotNull(actual);

    }

    @SneakyThrows
    @Test
    void objectMapperUrls() {
        var json = """
                {
                  "content" : "This is a sample document content.",
                  "urls" : [ "http://therevelationsquad.com/","https://github.com/ggreen?fg_force_rendering_mode=Images&fireglass_rsn=true#fireglass_params&tabid=a2c8ae713fb7ae4e&start_with_session_counter=2&application_server_address=isolation-3-us-east4.prod.fire.glass"
                  ]
                }
                """;

        var source = objectMapper.readValue(json, DocumentSource.class);
        Debugger.println(source.urls()[0]);
        var actual = subject.convert(source);
        assertNotNull(actual);
    }
}