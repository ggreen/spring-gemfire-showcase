package spring.gemfire.showcase.vector.search.services;

import org.springframework.ai.document.Document;

import java.util.List;

public interface SimilaritiesService {
    List<Document> findSimilarities(String question);
}
