package spring.gemfire.vector.sink.converter;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import spring.gemfire.vector.sink.domain.DocumentSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts DocumentSource to List of Document
 * @author Gregory Green
 */
@Component
@Slf4j
public class DocumentSourceToDocuments implements Converter<DocumentSource, List<Document>> {
    @Override
    public List<Document> convert(DocumentSource sourceObject) {

        log.info("Converting DocumentSource: {}",sourceObject);

        var docs = new ArrayList<Document>();
        constructFromContent(docs,sourceObject.content());

        constructFromUrls(docs,sourceObject.urls());
        return docs;
    }

    private void constructFromUrls(List<Document> docs, URL[] urls) {
        if(urls == null || urls.length == 0)
            return;

        for(URL url : urls)
        {
            if (url == null)
                continue;

            log.info("Reading URL: {}",url);

            var resource = new UrlResource(url);

            TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
            var readList = tikaDocumentReader.read();
            if(readList != null)
                docs.addAll(readList);
        }
    }

    private void constructFromContent(List<Document> docs, String content) {
        if(content == null || content.isBlank())
            return;

        log.info("Adding content: {}",content);
        docs.add(Document.builder().text(content).build());
    }
}
