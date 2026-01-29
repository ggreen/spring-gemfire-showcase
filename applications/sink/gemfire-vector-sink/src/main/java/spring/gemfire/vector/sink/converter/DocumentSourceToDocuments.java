package spring.gemfire.vector.sink.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.util.Text;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Converts DocumentSource to List of Document
 *
 * @author Gregory Green
 */
@Component
@Slf4j
public class DocumentSourceToDocuments implements Converter<String, List<Document>> {

    private final int contentMaxLengthBatchSize;

    public DocumentSourceToDocuments(@Value("${gemfire.vector.max.batch.length:2000}") int contentMaxLengthBatchSize) {
        this.contentMaxLengthBatchSize = contentMaxLengthBatchSize;
    }

    @SneakyThrows
    @Override
    public List<Document> convert(String source) {

        log.info("Converting DocumentSource: {}", source);

        source = source.trim();

        var docs = new ArrayList<Document>();
        if (source.startsWith("http"))
            constructFromUrls(docs, new URL(source));
        else {
            log.info("Adding content: {}", source);
            docs.add(Document.builder().text(source).build());
        }
        return docs;
    }

    private void constructFromUrls(List<Document> docs, URL url) {

        log.info("Reading URL: {}", url);
        var resource = new UrlResource(url);

        var tikaDocumentReader = new TikaDocumentReader(resource);
        var readList = tikaDocumentReader.read();


        docs.addAll(splitByBatchSize(readList));
    }

    private Collection<Document> splitByBatchSize(List<Document> readList) {

        List<Document> splitDocs = new ArrayList<>();
        for (var doc : readList) {
            if(doc.isText()
                    && doc.getText() != null
                    && doc.getText().length() > this.contentMaxLengthBatchSize) {

                splitDocs.addAll(divideDocsByMaxLength(doc.getText()));
            }else
                splitDocs.add(doc);
        }

        return splitDocs;
    }

    protected List<Document> divideDocsByMaxLength(String text) {

        log.debug("dividing doc with length: {} by max length: {}", text.length(),contentMaxLengthBatchSize);

        List<Document> results = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += contentMaxLengthBatchSize) {
            var batchText = text.substring(i, Math.min(length, i + contentMaxLengthBatchSize));
            log.info("Adding batchText: {}", batchText);
            results.add(new Document(batchText));
        }

        return results;
    }
}