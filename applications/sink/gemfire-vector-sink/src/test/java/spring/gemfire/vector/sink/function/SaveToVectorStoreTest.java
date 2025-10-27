package spring.gemfire.vector.sink.function;

import nyla.solutions.core.patterns.conversion.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import spring.gemfire.vector.sink.service.CleanupService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveToVectorStoreTest {

    private SaveToVectorStore subject;
    @Mock
    private VectorStore vectorDataStore;

    @Mock
    CleanupService cleanupService;

    @Mock
    private Converter<String, List<Document>> converter;

    private final static String documentSource = "junit";
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

        verify(cleanupService).removeSimilarDocs(any());
        verify(vectorDataStore).add(any(List.class));
    }

    @Test
    void givenNullContentThenDoNotSave() {
        subject.accept(null);

        verify(cleanupService,never()).removeSimilarDocs(any());
        verify(vectorDataStore,never()).add(any(List.class));
    }

    @Test
    void givenEmptyStringContentThenDoNotSave() {
        subject.accept("");

        verify(cleanupService,never()).removeSimilarDocs(any());
        verify(vectorDataStore,never()).add(any(List.class));
    }
    @Test
    void givenWhiteSpaceStringContentThenDoNotSave() {
        subject.accept("   ");

        verify(cleanupService,never()).removeSimilarDocs(any());
        verify(vectorDataStore,never()).add(any(List.class));
    }

}