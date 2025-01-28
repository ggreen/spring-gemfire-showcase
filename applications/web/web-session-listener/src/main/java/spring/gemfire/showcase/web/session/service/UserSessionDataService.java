package spring.gemfire.showcase.web.session.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spring.gemfire.showcase.web.session.domain.UserSession;
import spring.gemfire.showcase.web.session.domain.UserSessionStartEvent;
import spring.gemfire.showcase.web.session.repository.UserSessionRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Async
public class UserSessionDataService implements UserSessionService {

    private final UserSessionRepository repository;

    @Override
    public void saveStart(UserSessionStartEvent startEvent) {

        log.info("Start event: {}",startEvent);

        var userId = startEvent.getUserId();

        if(userId == null || userId.isEmpty())
            return;

        var builder = UserSession.builder();
        builder.userId(userId)
                .startTimeMs(startEvent.getStartTimeMs())
                .sessionId(startEvent.getSessionId());
        builder.durationMs(null);
        builder.endTimeMs(null);


        var userSession = builder.build();
        log.info("Saving session: {}",userSession);

        repository.save(userSession);

        log.info("Saved session: {}",startEvent);

    }


    public void saveEnd(String sessionId){

        var userSessionOptional = repository.findById(sessionId);

        if(userSessionOptional.isPresent())
        {
            //update
            var userSession = userSessionOptional.get();

            userSession.setEndTimeMs(System.currentTimeMillis());
            var durationMs= userSession.getEndTimeMs() - userSession.getStartTimeMs();
            userSession.setDurationMs(durationMs);

            repository.save(userSession);

            log.info("Duration :{} millisecond, for User details: {}", durationMs,userSession);
        }

    }

}

