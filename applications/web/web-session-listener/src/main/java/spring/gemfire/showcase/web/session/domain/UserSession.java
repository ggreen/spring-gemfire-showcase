package spring.gemfire.showcase.web.session.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSession {
    @Id
    private String sessionId;
    private String userId;
    private long startTimeMs;
    private Long endTimeMs;
    private Long durationMs;
}
