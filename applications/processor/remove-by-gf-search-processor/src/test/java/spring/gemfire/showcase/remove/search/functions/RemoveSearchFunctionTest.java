package spring.gemfire.showcase.remove.search.functions;

import nyla.solutions.core.patterns.creational.Maker;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.apache.geode.cache.lucene.LuceneQueryException;
import org.apache.geode.cache.lucene.LuceneResultStruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private Maker<String, LuceneQuery<Object, Object>> maker;

    @Mock
    private Region<Object, Object> region;

    @Mock
    private LuceneQuery<Object, Object> query;
    @Mock
    private LuceneResultStruct<Object, Object> result;

    private final static Object key = "key";

    @BeforeEach
    void setUp() {
        subject = new RemoveSearchFunction(maker,region);
    }

    @Test
    void apply() throws LuceneQueryException {

        when(maker.make(anyString())).thenReturn(query);
        List<Object> results = List.of(key);
        when(query.findKeys()).thenReturn(results);

        var actual = subject.apply(prompt);

        assertThat(actual).isEqualTo(prompt);
        verify(region).removeAll(any(Collection.class));

    }

    @Test
    void doNotRemoveWhenEmptyKeys() throws LuceneQueryException {

        when(maker.make(anyString())).thenReturn(query);
        List<Object> results = List.of();
        when(query.findKeys()).thenReturn(results);

        var actual = subject.apply(prompt);

        assertThat(actual).isEqualTo(prompt);
        verify(region,never()).removeAll(any(Collection.class));
    }
}