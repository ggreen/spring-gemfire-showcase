package spring.gemfire.showcase.web.session.service;

import spring.gemfire.showcase.web.session.domain.UserSessionRecord;

/**
 * @author Gregory Green
 */
public interface UserSessionReportService {

    /**
     * Report the creation of a user session
     * @param userSessionRecord the users session details
     */
    public void reportCreate(UserSessionRecord userSessionRecord);

    /**
     * Report the deletion of a session
     * @param sessionId the sessionId
     */
    void reportDestroyedSession(String sessionId);
}
