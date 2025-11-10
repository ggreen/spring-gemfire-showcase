package spring.gemfire.showcase.remove.search.functions;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.apache.geode.cache.lucene.LuceneQueryException;
import org.apache.geode.cache.lucene.LuceneResultStruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.remove.search.service.HyperTextService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveSearchFunctionTest {

    private final static String prompt = """
            Junit testing
            """;
    private RemoveSearchFunction subject;

    @Mock
    private Region<Object, Object> region;

    @Mock
    private LuceneQuery<Object, Object> query;
    @Mock
    private LuceneResultStruct<Object, Object> result;

    @Mock
    private HyperTextService service;
    private final static Object key = "key";

    @BeforeEach
    void setUp() {
        subject = new RemoveSearchFunction(service,region);
    }

    @Test
    void apply() throws LuceneQueryException {

        when(service.search(anyString())).thenReturn(query);
        List<Object> results = List.of(key);
        when(query.findKeys()).thenReturn(results);

        var actual = subject.apply(prompt);

        assertThat(actual).isEqualTo(prompt);
        verify(region).removeAll(any(Collection.class));

    }

    @Test
    void doNotRemoveWhenEmptyKeys() throws LuceneQueryException {

        var results = List.of();
        var actual = subject.apply(prompt);

        assertThat(actual).isEqualTo(prompt);
        verify(region,never()).removeAll(any(Collection.class));
    }

    @Test
    void whenMakerNull() {

        String expected = "JUNIT";
        assertThat(subject.apply(expected)).isEqualTo(expected);
    }

    @Test
    void givenUrlWhenApplyThenGetSummaryContentFromUrl() {
        var urls = "https://blogs.vmware.com/tanzu/introducing-vmware-gemfire-10-ga/";
        
        var actual = subject.apply(urls);
        
        assertThat(actual).isNotEmpty();
        
        verify(service).getSummary(any());
        
        
    }
}