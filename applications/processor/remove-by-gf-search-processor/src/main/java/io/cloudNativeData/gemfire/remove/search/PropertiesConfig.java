package io.cloudNativeData.gemfire.remove.search;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import io.cloudNativeData.gemfire.remove.search.properties.RemoveSearchProperties;

@Configuration
@EnableConfigurationProperties(RemoveSearchProperties.class)
public class PropertiesConfig {
}
