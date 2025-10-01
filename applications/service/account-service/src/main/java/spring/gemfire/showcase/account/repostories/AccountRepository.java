package spring.gemfire.showcase.account.repostories;

import org.springframework.data.domain.*;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import spring.gemfire.showcase.account.domain.account.Account;

import java.net.ContentHandler;
import java.util.List;


@Repository
    public interface AccountRepository
    extends GemfireRepository<Account,String>, PagingAndSortingRepository<Account,String>
    {
        List<Account> findByNameContaining(String name);

        Window<Account> findFirst2ByNameLikeOrderByName(String name, ScrollPosition offset);
    }
