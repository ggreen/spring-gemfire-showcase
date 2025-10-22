package spring.gemfire.vector.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The GemFireVectorSinkApp class is a Spring Boot application that serves as the entry point for the GemFire Vector Sink application.
 * It is responsible for bootstrapping the application context and starting the application.
 * @author Gregory Green
 */
@SpringBootApplication
public class GemFireVectorSinkApp {

	public static void main(String[] args) {
		SpringApplication.run(GemFireVectorSinkApp.class, args);
	}

}
