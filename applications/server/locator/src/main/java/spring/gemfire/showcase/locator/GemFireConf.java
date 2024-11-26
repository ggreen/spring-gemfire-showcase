package spring.gemfire.showcase.locator;

import org.apache.geode.distributed.LocatorLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
                .set("jmx-manager-start","true")
                .build();

        locatorLauncher.start();

        return locatorLauncher;
    }

}
