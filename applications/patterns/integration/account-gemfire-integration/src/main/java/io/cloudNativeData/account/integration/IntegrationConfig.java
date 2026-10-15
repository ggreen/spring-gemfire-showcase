package io.cloudNativeData.account.integration;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.dsl.Jdbc;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class IntegrationConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/appdb?allowPublicKeyRetrieval=true&useSSL=false")
                .username("root")
                .password("CHANGEME")
                .build();
    }


    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public IntegrationFlow gemFireInboundFlow(DataSource dataSource, ClientCache clientCache,
                                              PlatformTransactionManager transactionManager) {

        return IntegrationFlow
                .from(Jdbc.inboundAdapter(dataSource, "select * from item where status=2")
                                .updateSql("update item set status=10 where id in (:id)"),
                        e -> e.poller(Pollers.fixedDelay(5000)
                                .transactional(transactionManager)))

                .split()
                        .handle( (Map<Object,Object> map, MessageHeaders header) -> {

                             log.info("Captured Payload: {}",map.toString());

                            Region<Object,Map<Object,Object>> region = clientCache.getRegion("Items");

                            region.put(map.get("id"),new HashMap<>(map));
                            return null;
        } )
                .get();
    }

}
