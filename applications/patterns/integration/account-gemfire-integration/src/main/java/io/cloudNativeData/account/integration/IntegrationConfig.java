package io.cloudNativeData.account.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.dsl.Jdbc;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Value("${app.batch.size:500}")
    private int batchSize;

    @Value("${app.sql.select}")
    private String selectQuery;

    @Value("${app.sql.update}")
    private String updateSQL;

    @Bean
    public PlatformTransactionManager transactionManager(
            DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public IntegrationFlow gemFireInboundFlow(
            DataSource dataSource,
            @Qualifier("itemsTemplate")
            GemfireTemplate gemfireTemplate,
            PlatformTransactionManager transactionManager) {

        return IntegrationFlow
                .from(
                        Jdbc.inboundAdapter(
                                        dataSource,
                                        selectQuery)
                                .updateSql(
                                        updateSQL),
                        endpoint -> endpoint.poller(
                                Pollers.fixedDelay(1000)
                                        .transactional(transactionManager)))
                .split()
                .handle((Map<Object, Object> item,
                         MessageHeaders headers) -> {
                    log.info("Saving item: {}", item);
                    gemfireTemplate.put(item.get("id"),item);
                    return null;
                })
                .get();
    }
}
