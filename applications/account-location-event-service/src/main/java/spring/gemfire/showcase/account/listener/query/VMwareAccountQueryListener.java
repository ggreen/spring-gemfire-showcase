package spring.gemfire.showcase.account.listener.query;

import spring.gemfire.showcase.account.domain.account.Location;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.query.CqEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;

@Component
@Slf4j
public class VMwareAccountQueryListener {

    private final GemfireTemplate locationTemplate;

    public VMwareAccountQueryListener(@Qualifier("locationTemplate") GemfireTemplate locationTemplate) {
        this.locationTemplate = locationTemplate;
    }

    @ContinuousQuery(name = "addVMwareLocation", query = "select * from /Account where name = 'VMware' ", durable = true)
    public void addVMwareLocation(CqEvent cqEvent) {
        log.info("==============Adding location!!!!!");

        var key = cqEvent.getKey();
        var location = Location.builder()
                .id(valueOf(key))
                .address("3401 Hillview Ave")
                .city("Palo Alto")
                .stateCode("CA")
                .zipCode("94304").build();

        locationTemplate.put(key,location);
    }
}
