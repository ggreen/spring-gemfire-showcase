package spring.gemfire.vector.sink.function;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import spring.gemfire.vector.sink.domain.DocumentSource;
import spring.gemfire.vector.sink.service.CleanupService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveToVectorStoreTest {

    private SaveToVectorStore subject;
    @Mock
    private VectorStore vectorDataStore;

    @Mock
    CleanupService cleanupService;

    @Mock
    private Converter<DocumentSource, List<Document>> converter;

    private final static DocumentSource documentSource = JavaBeanGeneratorCreator.of(DocumentSource.class).create();
    private final static Document document = new Document("JUNIT");
    private final static List<Document> docs = List.of(document);

    @BeforeEach
    void setUp() {
        subject = new SaveToVectorStore(vectorDataStore,converter, cleanupService);
    }

    @Test
    void save() {

        when(converter.convert(any())).thenReturn(docs);

        subject.accept(documentSource);

        verify(cleanupService).removeSimilarDocs(any(DocumentSource.class));
        verify(vectorDataStore).add(any(List.class));
    }
}