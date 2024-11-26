package spring.gemfire.showcase.locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocatorServer
{
	public static void main(String[] args) {
		SpringApplication.run(LocatorServer.class, args);
	}

}
