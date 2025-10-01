package spring.gemfire.showcase.audit.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.RegionEvent;
import org.springframework.geode.cache.AbstractCommonEventProcessingCacheListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditCacheListener extends AbstractCommonEventProcessingCacheListener<Object, Object> implements CacheListener<Object,Object> {
    @Override
    protected void processEntryEvent(EntryEvent<Object, Object> event, EntryEventType eventType) {
      log.info("AUDIT: key: {} eventType: {} newValue: {} oldValue: {}",event.getKey(),eventType,event.getNewValue(),event.getOldValue());
    }

    @Override
    protected void processRegionEvent(RegionEvent<Object, Object> event, RegionEventType eventType) {
        log.info("AUDIT: eventType: {} region: {}",eventType,event.getRegion().getName());
    }
}
