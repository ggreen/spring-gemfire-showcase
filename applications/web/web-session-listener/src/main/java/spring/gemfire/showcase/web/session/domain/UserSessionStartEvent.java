package spring.gemfire.showcase.web.session.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSessionStartEvent {

    private String sessionId;
    private String userId;
    private long startTimeMs;
}
