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

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringWebSessionListener extends CacheListenerAdapter<String,PdxInstance> {

    private final UserSessionReportService service;

    /**
     * Pdx Instance value field names
     * 0 = "id"
     * 1 = "creationTime"
     * 2 = "lastAccessedTime"
     * 3 = "maxInactiveIntervalInSeconds"
     * 4 = "principalName"
     * @param event
     */

    @Override
    public void afterCreate(EntryEvent<String,PdxInstance> event) {

        reportSession(event);
    }


    @Override
    public void afterDestroy(EntryEvent<String,PdxInstance> event) {

        service.reportDestoredSession(event.getKey());

    }


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
