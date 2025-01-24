package spring.gemfire.showcase.web.session.service;

import spring.gemfire.showcase.web.session.domain.UserSessionRecord;

public interface UserSessionReportService {
    public void reportCreate(UserSessionRecord userSessionRecord);

    void reportDestoredSession(String sessionId);
}
