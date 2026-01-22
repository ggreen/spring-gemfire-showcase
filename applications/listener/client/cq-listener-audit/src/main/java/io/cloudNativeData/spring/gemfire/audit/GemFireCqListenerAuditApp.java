package io.cloudNativeData.spring.gemfire.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GemFireCqListenerAuditApp {

    public static void main(String[] args) {
        SpringApplication.run(GemFireCqListenerAuditApp.class,args);
    }
}
