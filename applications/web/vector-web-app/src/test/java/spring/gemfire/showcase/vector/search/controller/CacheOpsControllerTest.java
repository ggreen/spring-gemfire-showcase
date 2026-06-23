package spring.gemfire.showcase.vector.search.controller;

import org.apache.geode.cache.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.vector.search.services.ClearCacheService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheOpsControllerTest {

    private CacheOpsController subject;

    @Mock
    private ClearCacheService cacheService;

    @Mock
    private Region<Object, Object> region;

    @BeforeEach
    void setUp() {
        subject = new CacheOpsController(cacheService);
    }

    @Test
    void clearVectorEmbeddings() {

        subject.clearCache();

        verify(cacheService).clearCache();
    }
}