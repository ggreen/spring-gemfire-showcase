package spring.gemfire.showcase.audit.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.query.CqListener;
import org.apache.geode.cache.util.CqListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CqAuditListener extends CqListenerAdapter implements CqListener {
    public void onEvent(CqEvent cqEvent) {
        log.info("AUDIT: CQ Event - key: {} newValue: {} cq: {}",
                cqEvent.getKey(),cqEvent.getNewValue(),cqEvent.getCq());
    }
}
