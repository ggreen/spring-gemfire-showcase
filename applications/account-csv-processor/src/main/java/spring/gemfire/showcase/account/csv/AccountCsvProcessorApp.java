package spring.gemfire.showcase.account.csv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class AccountCsvProcessorApp {

	public static void main(String[] args) {

		log.info("****** PROPERTIES: {}\n\n\n ENV: {}",System.getProperties(),System.getenv());
		SpringApplication.run(AccountCsvProcessorApp.class, args);
	}

}
