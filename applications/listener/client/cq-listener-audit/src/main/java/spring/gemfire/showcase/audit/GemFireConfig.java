package spring.gemfire.showcase.audit;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import spring.gemfire.showcase.audit.listener.CqAuditListener;

@Configuration
@ClientCacheApplication(subscriptionEnabled = true, readyForEvents = true)
public class GemFireConfig {


    @Value("${spring.application.name:cq-listener-audit}")
    private String cqName;

    @Value("${gemfire.cq.oql}")
    private String oql;

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
            var cqQuery = queryService.newCq(cqName, oql, cqa);

            // Execute CQ, getting the optional initial result set
            // Without the initial result set, the call is priceTracker.execute();
            cqQuery.execute();

            return cqQuery;

    }

}
