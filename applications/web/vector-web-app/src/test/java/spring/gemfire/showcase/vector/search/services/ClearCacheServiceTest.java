package spring.gemfire.showcase.vector.search.services;

import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.gemfire.GemfireTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClearCacheServiceTest {

    private ClearCacheService subject;

    @Mock
    private GemfireTemplate vectorStoreTemplate;
    @Mock
    private GemfireTemplate searchResultsTemplate;

    @Mock
    private Region<Object, Object> vectorStoreRegion;

    @Mock
    private Region<Object, Object> searchResultsRegion;


    @BeforeEach
    void setUp() {
        subject = new ClearCacheService(vectorStoreTemplate,
                searchResultsTemplate);
    }

    @Test
    void clearCache() {

        when(vectorStoreTemplate.getRegion()).thenReturn(vectorStoreRegion);
        when(searchResultsTemplate.getRegion()).thenReturn(searchResultsRegion);

        subject.clearCache();

        verify(vectorStoreTemplate).removeAll(any());
        verify(searchResultsTemplate).removeAll(any());
    }
}