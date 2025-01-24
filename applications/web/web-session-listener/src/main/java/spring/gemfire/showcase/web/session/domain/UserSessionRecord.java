package spring.gemfire.showcase.web.session.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionRecord{
    private String sessionId;
    private String userId;
    private long creationEpoc;
}
