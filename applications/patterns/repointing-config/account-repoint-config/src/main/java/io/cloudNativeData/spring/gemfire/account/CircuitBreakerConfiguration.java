package io.cloudNativeData.spring.gemfire.account;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for initializing and customizing Spring Cloud Circuit Breakers
 * using the Resilience4j implementation.
 * <p>
 * This class establishes baseline defaults for all circuit breakers in the application context
 * and exposes specific pre-configured {@link CircuitBreaker} instances as shared, thread-safe beans.
 * </p>
 *
 * @author Gregory Green
 */
@Configuration
@Slf4j
public class CircuitBreakerConfiguration {

    @Value("${app.circuit.breaker.timeout.seconds:1}")
    private long timeOutSeconds;


    /**
     * Provides a global customizer that applies a default configuration to all
     * circuit breakers instantiated by the {@link Resilience4JCircuitBreakerFactory}.
     * <p>
     * Default configurations include:
     * <ul>
     * <li>A time-limiter timeout threshold of 1 second.</li>
     * </ul>
     * </p>
     *
     * @return a {@link Customizer} tailored for the default configurations of {@link Resilience4JCircuitBreakerFactory}
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(timeOutSeconds)) // <-- Sets timeout to 1 second
                        .cancelRunningFuture(false)             // Cancels the execution thread on timeout
                        .build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build());
    }

    /**
     * Creates and registers a shared, thread-safe {@link CircuitBreaker} instance
     * dedicated to handling read operations.
     *
     * @param cbFactory the factory used to manage and create circuit breaker instances
     * @return a {@link CircuitBreaker} configured under the name "readCircuitBreaker"
     */
    @Bean
    CircuitBreaker readCircuitBreaker(CircuitBreakerFactory<?,?> cbFactory) {
        return cbFactory.create("readCircuitBreaker");
    }

    /**
     * Creates and registers a shared, thread-safe {@link CircuitBreaker} instance
     * dedicated to handling write operations.
     *
     * @param cbFactory the factory used to manage and create circuit breaker instances
     * @return a {@link CircuitBreaker} configured under the name "writeCircuitBreaker"
     */
    @Bean
    CircuitBreaker writeCircuitBreaker(CircuitBreakerFactory<?,?>  cbFactory) {
        return cbFactory.create("writeCircuitBreaker");
    }
}
