package io.cloudNativeData.spring.gemfire.audit.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.RegionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.geode.cache.AbstractCommonEventProcessingCacheListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditCacheListener extends AbstractCommonEventProcessingCacheListener<Object, Object> implements CacheListener<Object,Object> {

    private final String durableClientId;
    public AuditCacheListener(    @Value("${spring.data.gemfire.cache.client.durable-client-id}")
                                  String durableClientId) {
        this.durableClientId = durableClientId;
    }

    @Override
    protected void processEntryEvent(EntryEvent<Object, Object> event, EntryEventType eventType) {
      log.info("AUDIT: durableClientId: {} -  Entry Event key: {} eventType: {} newValue: {} oldValue: {}",
              durableClientId,
              event.getKey(),eventType,event.getNewValue(),event.getOldValue());
    }

    @Override
    protected void processRegionEvent(RegionEvent<Object, Object> event, RegionEventType eventType) {
        log.info("AUDIT: durableClientId: {} - Region Event - eventType: {} region: {}",durableClientId, eventType,event.getRegion().getName());
    }
}
