package spring.gemfire.showcase.locator;

import org.apache.geode.distributed.LocatorLauncher;
import org.apache.geode.pdx.PdxSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@EnableGemfireRepositories
public class GemFireConf
{
    @Value("${gemfire.locator.name}")
    private String locatorName;


    @Value("${spring.data.gemfire.pool.locators}")
    private String locators;


    @Value("${gemfire.pdx.patterns:.*}")
    private String pdxClassPatterns;

    @Value("${gemfire.locator.port:40404}")
    private Integer locatorPort;


    @Bean
    LocatorLauncher builder()
    {

        var locatorLauncher = new LocatorLauncher.Builder()
                .setMemberName(locatorName)
                .setPort(locatorPort)
                .build();

        locatorLauncher.start();

        return locatorLauncher;
    }

}
