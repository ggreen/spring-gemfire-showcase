package spring.gemfire.showcase.account.controller;

import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.lucene.LuceneQueryFactory;
import org.apache.geode.cache.lucene.LuceneResultStruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cacheable/account/search")
@Slf4j
public class FavoriteAccountsSearchCacheableController {

    private final LuceneQueryFactory luceneQueryFactory;
    private final String indexName;
    private final String regionName;
    private final String defaultField;

    public FavoriteAccountsSearchCacheableController(LuceneQueryFactory luceneQueryFactory,
                                                     @Value("${app.account.search.index.name}") String indexName,
                                                     @Value("${app.account.search.region.name}")String regionName,
                                                     @Value("${app.account.search.default.field}")String defaultField) {
        this.luceneQueryFactory = luceneQueryFactory;
        this.indexName = indexName;
        this.regionName = regionName;
        this.defaultField = defaultField;
    }

    /**
     * @param nameQuery ex: "name:John AND zipcode:97006"
     * @return search results
     */
    @SneakyThrows
    @PostMapping
    public List<LuceneResultStruct<Object, Object>> searchByNameQuery(@RequestParam String nameQuery,
                                                                      HttpSession httpSession)
    {
        var query = this.luceneQueryFactory.create(indexName,
                regionName, nameQuery, defaultField);

        var pages = query.findPages();

        if(!pages.hasNext())
            return null;

        var index = 0;
        while(pages.hasNext())
        {
            var results = pages.next();
            httpSession.setAttribute("pageAccounts"+index++,results);
        }
        return (List<LuceneResultStruct<Object, Object>>) httpSession.getAttribute("pageAccounts"+0);
    }

}
