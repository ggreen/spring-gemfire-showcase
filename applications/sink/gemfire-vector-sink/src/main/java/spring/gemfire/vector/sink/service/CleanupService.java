package spring.gemfire.vector.sink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CleanupService {
    private final VectorStore vectorStore;
    @Value("${ai.cleanup.search.threshold:0.8}")
    private double similarCleanupThreshold;

    public List<String> removeSimilarDocs(String contentOrUrl) {

        if(contentOrUrl ==null || contentOrUrl.isEmpty())
            return null;

        log.info("Searching for similar documents to remove based on content: {}",contentOrUrl);
        var results = vectorStore.similaritySearch(SearchRequest
                .builder()
                        .similarityThreshold(similarCleanupThreshold)
                .build());

        if(results == null  || results.isEmpty())
            return null;

        var idsToDelete = results.stream().map(Document::getId).toList();
        log.info("Removing ids: {}",idsToDelete);

        vectorStore.delete(idsToDelete);
        return idsToDelete;
    }
}
