package spring.gemfire.vector.sink.function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import spring.gemfire.vector.sink.domain.DocumentSource;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveToVectorStore implements Consumer<DocumentSource> {

    private final VectorStore vectorDataStore;
    private final Converter<DocumentSource, List<Document>> ToDocs;

    @Override
    public void accept(DocumentSource documentSource) {
        log.info("doc source: {}", documentSource);

        vectorDataStore.add(ToDocs.convert(documentSource));
    }
}
