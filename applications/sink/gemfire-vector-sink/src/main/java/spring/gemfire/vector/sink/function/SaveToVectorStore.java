package spring.gemfire.vector.sink.function;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import spring.gemfire.vector.sink.service.CleanupService;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveToVectorStore implements Consumer<String> {

    private final VectorStore vectorDataStore;
    private final Converter<String, List<Document>> ToDocs;
    private final CleanupService cleanupService;

    @Override
    public void accept(String contentOrUrl) {
        log.info("doc source: {}", contentOrUrl);

        cleanupService.removeSimilarDocs(contentOrUrl);

        vectorDataStore.add(ToDocs.convert(contentOrUrl));
    }
}
