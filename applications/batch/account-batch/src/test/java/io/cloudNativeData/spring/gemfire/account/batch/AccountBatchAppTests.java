package io.cloudNativeData.spring.gemfire.account.batch;

import com.vmware.gemfire.testcontainers.GemFireCluster;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.lang.System.setProperty;

//@SpringBootTest
//@Log4j2
//@Testcontainers
class AccountBatchAppTests {

	private static GemFireCluster gemFireCluster;
	private static int locatorCount = 1;
	private static int serverCount = 1;
	private static String hostName = "localhost";
	private static String batchJdbcUrl = "jdbc:h2:mem:account_batch";

	private String ddlSql = """
			create schema IF NOT EXISTS cache_accounts;
			
			CREATE TABLE cache_accounts.account (
			    id text NOT NULL,
			    name text NOT NULL,
			    cus_fn text NOT NULL,
			    cus_ln text NOT NULL,
			    con_email text NOT NULL,
			    con_phone text NOT NULL,
			    PRIMARY KEY (ID)
			);
			""";

//	@BeforeAll
	static void beforeAll() {
		setProperty("batch.jdbc.url",batchJdbcUrl);
		setProperty("batch.job.repository.create","true");

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



//	@BeforeEach
	void setUp() {

		var dataSource = DataSourceBuilder.create().
				url(batchJdbcUrl).build();

		var jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute(ddlSql);
	}

//	@Test
	void contextLoads() {
	}

}
