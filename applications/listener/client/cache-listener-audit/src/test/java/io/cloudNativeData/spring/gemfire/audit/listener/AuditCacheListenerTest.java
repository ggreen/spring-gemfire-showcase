package io.cloudNativeData.spring.gemfire.audit.listener;

import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.geode.cache.AbstractCommonEventProcessingCacheListener;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditCacheListenerTest {

    private AuditCacheListener subject;

    @Mock
    private EntryEvent<Object, Object> event;
    @Mock
    private RegionEvent<Object, Object> regionEvent;
    private final static AbstractCommonEventProcessingCacheListener.EntryEventType type = AbstractCommonEventProcessingCacheListener.EntryEventType.CREATE;

    private AbstractCommonEventProcessingCacheListener.RegionEventType regionEventType = AbstractCommonEventProcessingCacheListener.RegionEventType.DESTROY;
    @Mock
    private Region<Object, Object> region;

    @BeforeEach
    void setUp() {
        subject = new AuditCacheListener("junit-test-client");
    }

    @Test
    void events() {
        subject.processEntryEvent(event, type);
        verify(event).getNewValue();
    }

    @Test
    void regions() {
        when(regionEvent.getRegion()).thenReturn(region);
        subject.processRegionEvent(regionEvent,regionEventType);
        verify(region).getName();
    }
}