package spring.gemfire.showcase.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountJdbcCachingServiceApp
{
	public static void main(String[] args) {
		SpringApplication.run(AccountJdbcCachingServiceApp.class, args);
	}

}
