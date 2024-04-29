package spring.gemfire.showcase.account.sink;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AccountSinkApp {

	public static void main(String[] args) {

		log.info("****** PROPERTIES: {}\n\n\n ENV: {}",System.getProperties(),System.getenv());
		SpringApplication.run(AccountSinkApp.class, args);
	}

}
