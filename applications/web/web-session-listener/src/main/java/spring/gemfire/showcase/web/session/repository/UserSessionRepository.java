package spring.gemfire.showcase.web.session.repository;

import org.springframework.data.repository.CrudRepository;
import spring.gemfire.showcase.web.session.domain.UserSession;

public interface UserSessionRepository extends CrudRepository<UserSession, String> {
}
