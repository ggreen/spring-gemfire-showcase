package spring.gemfire.vector.sink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import spring.gemfire.vector.sink.domain.DocumentSource;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CleanupService {
    private final VectorStore vectorStore;

    public List<String> removeSimilarDocs(DocumentSource documentSource) {
        if(documentSource == null)
            return null;

        var content = documentSource.content();

        if(content ==null || content.isEmpty())
            return null;

        log.info("Searching for similar documents to remove based on content: {}",content);
        var results = vectorStore.similaritySearch(content);

        if(results == null  || results.isEmpty())
            return null;

        var idsToDelete = results.stream().map(Document::getId).toList();
        log.info("Removing ids: {}",idsToDelete);

        vectorStore.delete(idsToDelete);
        return idsToDelete;
    }
}
