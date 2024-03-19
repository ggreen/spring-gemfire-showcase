package com.vmware.spring.geode.showcase.account;

import com.vmware.gemfire.testcontainers.GemFireClusterContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Testcontainers
class AccountServiceAppTest {

    private static GemFireClusterContainer<?> cluster;

//    @BeforeAll
    public static void setup()
    {

        cluster = new GemFireClusterContainer<>();
        cluster.acceptLicense();
        cluster.withExposedPorts(10433);
        cluster.start();

        cluster.gfsh(
                true,
                "list members",
                "create region --name=Account --type=PARTITION",
                "describe region --name=Account"

        );
        cluster.gfsh(
                true,
                "describe connection"
        );


    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.gemfire.pool.default.locators",
                () -> "localhost"+"["+cluster.getLocatorPort()+"]");
    }


//    @Test
    void contextLoads() {
    }

//    @AfterAll
    static void shutdown()
    {
        cluster.close();
    }
}