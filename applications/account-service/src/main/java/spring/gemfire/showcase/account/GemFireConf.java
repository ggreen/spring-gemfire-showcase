package spring.gemfire.showcase.account;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.pdx.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import spring.gemfire.showcase.account.domain.account.Account;

@ClientCacheApplication
@EnableSecurity
@Configuration
@EnablePdx(serializerBeanName = "pdxSerializer",
        readSerialized = true,
        includeDomainTypes = Account.class)
@EnableGemfireRepositories
public class GemFireConf
{
    private final PdxSerializer mappingPdxSerializer= new ReflectionBasedAutoSerializer(".*",
            Account.class.getName());

    @Bean("pdxSerializer")
    PdxSerializer pdxSerializer()
    {
        return new PdxSerializer() {
            @Override
            public boolean toData(Object o, PdxWriter pdxWriter) {
                return mappingPdxSerializer.toData(o,pdxWriter);
            }

            @Override
            public Object fromData(Class<?> aClass, PdxReader pdxReader) {

                //NOT BEING CALLED
                var value = mappingPdxSerializer.fromData(aClass,pdxReader);
                if(value instanceof PdxInstance pdxInstance)
                    return pdxInstance.getObject();
                return value;
            }
        };
    }
    @Bean("Account")
    ClientRegionFactoryBean<String, Account> account(GemFireCache gemFireCache)
    {
        var bean = new ClientRegionFactoryBean<String,Account>();
        bean.setCache(gemFireCache);
        bean.setDataPolicy(DataPolicy.EMPTY);
        return bean;
    }
}
