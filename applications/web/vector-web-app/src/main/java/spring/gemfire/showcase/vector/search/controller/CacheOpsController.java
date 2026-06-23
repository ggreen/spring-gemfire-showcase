package spring.gemfire.showcase.vector.search.controller;

import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache/ops")
public class CacheOpsController {

    private final GemfireTemplate gemfireTemplate;

    public CacheOpsController(GemfireTemplate gemfireTemplate) {
        this.gemfireTemplate = gemfireTemplate;
    }

    @DeleteMapping
    public void clearCache() {

        var keys = gemfireTemplate.getRegion().keySetOnServer();

        gemfireTemplate.removeAll(keys);
    }
}
