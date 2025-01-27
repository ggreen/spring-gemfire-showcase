package spring.gemfire.showcase.web.session.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.pdx.PdxInstance;
import org.springframework.stereotype.Component;
import spring.gemfire.showcase.web.session.domain.UserSessionRecord;
import spring.gemfire.showcase.web.session.service.UserSessionReportService;

import java.time.Instant;

import static java.lang.String.valueOf;

/**
 * Listens for events on the GemFire region contains the session details
 * @author gregory green
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SpringWebSessionListener extends CacheListenerAdapter<String,PdxInstance> {

    private final UserSessionReportService service;

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

        reportSession(event);
    }


    /**
     * Process a session logout
     * @param event the key contains the session id, the value is blank
     */
    @Override
    public void afterDestroy(EntryEvent<String,PdxInstance> event) {

        service.reportDestroyedSession(event.getKey());

    }

    /**
     * Provide the session this to a service
     * @param event the session event
     */
    private void reportSession(EntryEvent<String, PdxInstance> event) {

        log.info("Created SESSION ID: {} ",event.getKey());
        var serializedNewValue =  event.getSerializedNewValue();
        if(serializedNewValue == null)
            return;

        var pdxInstance = serializedNewValue.getDeserializedValue();

        if(pdxInstance == null)
            return;;

        var userName = valueOf(pdxInstance.getField("principalName"));
        var sessionId = event.getKey();
        var creationTimeFromPdx = pdxInstance.getField("creationTime");
        var creationTimeEpoc = System.currentTimeMillis();

        if(creationTimeFromPdx instanceof Instant createInstance)
            creationTimeEpoc = createInstance.getEpochSecond();

        service.reportCreate(UserSessionRecord.builder()
                .sessionId(sessionId)
                .userId(userName)
                .creationEpoc(creationTimeEpoc)
                .build());
    }
}
