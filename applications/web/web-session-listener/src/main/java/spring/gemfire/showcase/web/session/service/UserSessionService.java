package spring.gemfire.showcase.web.session.service;

import spring.gemfire.showcase.web.session.domain.UserSessionStartEvent;

public interface UserSessionService {

    void saveStart(UserSessionStartEvent userSessionStartEvent);

    void saveEnd(String sessionId);
}
