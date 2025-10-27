package spring.gemfire.showcase.remove.search.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "gemfire.remove.search")
public class RemoveSearchProperties {
    private String indexName;
    private String regionName;
    private String defaultField;
}
