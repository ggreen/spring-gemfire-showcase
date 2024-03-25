package spring.gemfire.batch.account.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountBatchApp {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(
				SpringApplication.run(
						AccountBatchApp.class, args)));
	}

}
