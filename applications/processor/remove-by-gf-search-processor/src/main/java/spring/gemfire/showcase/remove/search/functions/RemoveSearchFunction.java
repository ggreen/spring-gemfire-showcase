package spring.gemfire.showcase.remove.search.functions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.Maker;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.lucene.LuceneQuery;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Search from keys using GemFIre remove and remove keys from a given remove
 * @author Gregory Green
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveSearchFunction implements Function<String,String> {

    private final Maker<String,LuceneQuery<Object,Object>> queryMaker;
    private final Region<Object,Object> region;

    @SneakyThrows
    @Override
    public String apply(String text) {

        log.info("Searching for: {}",text);

        var query = queryMaker.make(text);
        var keys = query.findKeys();

        if(keys == null || keys.isEmpty())
            return text;

        log.info("Removing keys: {}",keys);
        region.removeAll(keys);

        return text;
    }
}
