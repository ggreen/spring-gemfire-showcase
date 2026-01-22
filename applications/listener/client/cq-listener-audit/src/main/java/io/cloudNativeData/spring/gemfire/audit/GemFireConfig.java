package io.cloudNativeData.spring.gemfire.audit;

import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import io.cloudNativeData.spring.gemfire.audit.listener.CqAuditListener;

@Slf4j
@Configuration
@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true)
public class GemFireConfig {


    @Value("${spring.application.name:cq-listener-audit}")
    private String cqName;

    @Value("${audit.cq.oql}")
    private String oql;

    @Value("${gemfire.cq.durable:true}")
    private boolean durable;

    @Bean
    CqQuery cqQuery(ClientCache clientCache,CqAuditListener cqAuditListener) throws CqException, CqExistsException, RegionNotFoundException
    {
            var queryService = clientCache.getQueryService();
            // Create CqAttribute using CqAttributeFactory
            CqAttributesFactory cqf = new CqAttributesFactory();

            // Create a listener and add it to the CQ attributes callback defined below
            cqf.addCqListener(cqAuditListener);
            var cqa = cqf.create();
            // Name of the CQ and its query

            // Create the CqQuery
            log.info("Creating CQ '{}' with OQL: {}, durable: {}", cqName, oql,durable);
            var cqQuery = queryService.newCq(cqName, oql, cqa,durable);

            // Execute CQ, getting the optional initial result set
            // Without the initial result set, the call is priceTracker.execute();
            cqQuery.execute();

            return cqQuery;

    }

}
