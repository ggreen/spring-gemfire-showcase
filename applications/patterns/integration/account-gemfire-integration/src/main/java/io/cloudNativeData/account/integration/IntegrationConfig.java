package io.cloudNativeData.account.integration;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.dsl.Jdbc;
import javax.sql.DataSource;
import org.springframework.integration.gemfire.outbound.CacheWritingMessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.List;
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
    public IntegrationFlow gemFireInboundFlow(DataSource dataSource, ClientCache clientCache) {

        return IntegrationFlow
                .from(Jdbc.inboundAdapter(dataSource, "select * from item where status=2")
                                .updateSql("update item set status=10 where id in (:id)"),
                        e -> e.poller(Pollers.fixedDelay(5000)))
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
