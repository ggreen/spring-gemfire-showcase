package spring.gemfire.showcase.remove.search;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import spring.gemfire.showcase.remove.search.properties.RemoveSearchProperties;

@Configuration
@EnableConfigurationProperties(RemoveSearchProperties.class)
public class PropertiesConfig {
}
