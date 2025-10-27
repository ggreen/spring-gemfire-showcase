package spring.gemfire.vector.sink.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts DocumentSource to List of Document
 *
 * @author Gregory Green
 */
@Component
@Slf4j
public class DocumentSourceToDocuments implements Converter<String, List<Document>> {
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

        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        var readList = tikaDocumentReader.read();
        docs.addAll(readList);
    }
}