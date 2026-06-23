package spring.gemfire.showcase.vector.search.controller;

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
class CacheOpsControllerTest {

    private CacheOpsController subject;

    @Mock
    private GemfireTemplate gemfireTemplate;

    @Mock
    private Region<Object, Object> region;

    @BeforeEach
    void setUp() {
        subject = new CacheOpsController(gemfireTemplate);
    }

    @Test
    void clearVectorEmbeddings() {

        when(gemfireTemplate.getRegion()).thenReturn(region);

        subject.clearCache();

        verify(gemfireTemplate).removeAll(any());
    }
}