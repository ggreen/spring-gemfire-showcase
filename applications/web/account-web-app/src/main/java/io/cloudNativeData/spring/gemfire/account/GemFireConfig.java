package io.cloudNativeData.spring.gemfire.account;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.session.data.gemfire.config.annotation.web.http.EnableGemFireHttpSession;


@ClientCacheApplication
@EnableGemFireHttpSession
@Configuration
public class GemFireConfig {
}
