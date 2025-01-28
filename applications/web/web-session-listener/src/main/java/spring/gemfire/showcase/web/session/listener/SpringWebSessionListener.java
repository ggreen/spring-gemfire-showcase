package spring.gemfire.showcase.web.session.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.util.Text;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.web.session.domain.UserSessionStartEvent;
import spring.gemfire.showcase.web.session.service.UserSessionService;

import java.time.Instant;

/**
 * Listens for events on the GemFire region contains the session details
 * @author gregory green
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringWebSessionListener extends CacheListenerAdapter<String,PdxInstance> {

    private final UserSessionService service;

    /**
     * Process a session creation event
     *
     * @param event the key is the session id and the value of contains session details in PDX format.
     * Pdx Instance value field names
     *      * 0 = "id"
     *      * 1 = "creationTime"
     *      * 2 = "lastAccessedTime"
     *      * 3 = "maxInactiveIntervalInSeconds"
     *      * 4 = "principalName"
     */
    @Override
    public void afterCreate(EntryEvent<String,PdxInstance> event) {

        log.info("Created SESSION ID: {} ",event.getKey());

        var pdxInstance = event.getNewValue();
        if(pdxInstance == null)
            return;

        var userSessionStartEventBuilder = UserSessionStartEvent.builder();

        userSessionStartEventBuilder.userId(Text.toString(pdxInstance.getField("principalName")));

        userSessionStartEventBuilder.sessionId(event.getKey());
        var creationTimeFromPdx = pdxInstance.getField("creationTime");
        userSessionStartEventBuilder.startTimeMs(System.currentTimeMillis());

        if(creationTimeFromPdx instanceof Instant createInstance)
            userSessionStartEventBuilder.startTimeMs(createInstance.getEpochSecond());

        service.saveStart(userSessionStartEventBuilder.build());
    }


    /**
     * Process a session logout
     * @param event the key contains the session id, the value is blank
     */
    @Override
    public void afterDestroy(EntryEvent<String,PdxInstance> event) {

        var sessionId = event.getKey();

        log.info("Destroyed session: {}",sessionId);
        service.saveEnd(sessionId);

    }
}
