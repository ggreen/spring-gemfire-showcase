package io.cloudNativeData.spring.gemfire.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GemFireListenerAuditApp {
    public static void main(String[] args) {
        SpringApplication.run(GemFireListenerAuditApp.class,args);
    }
}
