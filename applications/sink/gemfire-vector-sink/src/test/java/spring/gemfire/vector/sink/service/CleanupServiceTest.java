package spring.gemfire.vector.sink.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CleanupServiceTest {

    private CleanupService subject;
    private static final String docId = "doc01";
    private static final List<String> expected =  List.of(docId);
    @Mock
    private VectorStore vectorStore;

    @Mock
    private Document document;

    @BeforeEach
    void setUp() {
        subject = new CleanupService(vectorStore);
    }

    @Test
    void checkNullsAnEmpty() {

        assertThat(subject.removeSimilarDocs(null)).isNullOrEmpty();
        assertThat(subject.removeSimilarDocs("")).isNullOrEmpty();
    }

    @Test
    void removeSimilarDocs() {

        var documentSource ="content";

        List<Document> docLists = List.of(document);
        when(document.getId()).thenReturn(docId);
        when(vectorStore.similaritySearch(any(String.class))).thenReturn(docLists);

        var removed = subject.removeSimilarDocs(documentSource);

        assertThat(removed).isEqualTo(expected);
        verify(vectorStore).delete(any(List.class));

    }
}