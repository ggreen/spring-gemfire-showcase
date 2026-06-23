package spring.gemfire.showcase.vector.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gemfire.showcase.vector.search.services.ClearCacheService;

@RestController
@RequestMapping("cache/ops")
@RequiredArgsConstructor
public class CacheOpsController {

    private final ClearCacheService cacheService;

    @DeleteMapping
    public void clearCache() {

        cacheService.clearCache();
    }
}
