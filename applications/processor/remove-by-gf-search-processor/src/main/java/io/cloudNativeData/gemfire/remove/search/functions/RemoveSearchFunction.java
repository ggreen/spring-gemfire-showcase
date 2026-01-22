package io.cloudNativeData.gemfire.remove.search.functions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.Region;
import org.springframework.stereotype.Component;
import io.cloudNativeData.gemfire.remove.search.service.HyperTextService;

import java.net.URI;
import java.util.function.Function;

/**
 * Search from keys using GemFire remove and remove keys from a given remove
 * @author Gregory Green
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveSearchFunction implements Function<String,String> {

    private final HyperTextService service;
    private final Region<Object,Object> region;

    @SneakyThrows
    @Override
    public String apply(String text) {
        log.info("Searching for: {}",text);

        var searchContent = text.trim().toLowerCase();

        //Get URL content if needed
        if(searchContent.startsWith("http")) {
            searchContent = service.getSummary(new URI(searchContent));
            log.info("From Url searchContent: {}",searchContent);
        }

        var query = service.search(searchContent);
        if(query == null)
            return text;

        var keys = query.findKeys();
        if(keys == null || keys.isEmpty())
            return text;

        log.info("Removing keys: {}",keys);
        region.removeAll(keys);

        return text;
    }
}
