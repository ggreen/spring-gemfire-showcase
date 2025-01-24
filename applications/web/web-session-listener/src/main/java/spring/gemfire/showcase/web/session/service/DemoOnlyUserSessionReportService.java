package spring.gemfire.showcase.web.session.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.gemfire.showcase.web.session.domain.UserSessionRecord;

import java.util.HashMap;

import static java.lang.System.currentTimeMillis;

@Slf4j
@Service
public class DemoOnlyUserSessionReportService implements UserSessionReportService{

    private HashMap<String, UserSessionRecord> inMemoryOnly = new HashMap<>();

    @Override
    public void reportCreate(UserSessionRecord userSessionRecord) {
        inMemoryOnly.put(userSessionRecord.getSessionId(),userSessionRecord);

    }

    @Override
    public void reportDestoredSession(String sessionId) {

        var userSession = inMemoryOnly.get(sessionId);
        if(userSession == null)
            return;;

            var start = userSession.getCreationEpoc();
            var end = currentTimeMillis();

        log.info("User: {} Duration:{} milliseconds ",userSession.getUserId(),end-start);

    }
}
