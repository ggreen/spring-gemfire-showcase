package spring.gemfire.showcase.account;

import com.vmware.gemfire.testcontainers.GemFireCluster;
import lombok.SneakyThrows;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import spring.gemfire.showcase.account.domain.account.Account;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
/**
 *
 */
class AccountServiceAppTest {

    private static GemFireCluster gemFireCluster;
    private static int locatorCount = 1;
    private static int serverCount = 1;
    private static String hostName = "localhost";
//    @Autowired
    private TestRestTemplate restTemplate;

    private Account account;
    private String url = "/accounts";

    @SneakyThrows
//    @BeforeEach
    void setUp() {
        account = JavaBeanGeneratorCreator.of(Account.class).create();
    }

//    @BeforeAll
    public static void setup()
    {
        gemFireCluster = new GemFireCluster(System.getProperty("imageName","gemfire/gemfire:10.1-jdk17"), locatorCount, serverCount)
                .withGfsh(true, "create region --name=Account --type=PARTITION")
                .withPorts("locator-0",10334)
                .withPorts("server-0",40404)
                .withHostnameForClients("locator-0",hostName)
                .withHostnameForClients("server-0",hostName)
                .withGemFireProperty("locator-0", "jmx-manager-hostname-for-clients",hostName);



        gemFireCluster.acceptLicense().start();
        System.setProperty("spring.data.gemfire.pool.locators", String.format("localhost[%d]", gemFireCluster.getLocatorPort()));
    }


//    @Test
    void accountServiceCrud() {
        gemFireCluster.gfsh(true,"list regions");

        //Create
        var response = restTemplate.postForObject(url,account, Account.class);
        assertThat(response).isNotNull();

        //Read
        var actual = restTemplate.getForObject(url+"/"+account.getId(),Account.class);
        assertThat(actual).isEqualTo(response);

        //Update
        account.setName(actual.getName()+" UPDATED");
        restTemplate.postForObject(url,account, Account.class);
        actual = restTemplate.getForObject(url+"/"+account.getId(),Account.class);
        assertThat(actual).isEqualTo(account);

        //Delete
        restTemplate.delete(url+"/"+account.getId());
        actual = restTemplate.getForObject(url+"/"+account.getId(),Account.class);
        assertThat(actual).isNull();
    }

//    @AfterAll
    static void shutdown()
    {
        gemFireCluster.close();
    }
}