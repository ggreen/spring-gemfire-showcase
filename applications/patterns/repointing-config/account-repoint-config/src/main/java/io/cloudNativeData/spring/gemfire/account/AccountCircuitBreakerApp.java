package io.cloudNativeData.spring.gemfire.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountCircuitBreakerApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountCircuitBreakerApp.class,args);
    }
}
