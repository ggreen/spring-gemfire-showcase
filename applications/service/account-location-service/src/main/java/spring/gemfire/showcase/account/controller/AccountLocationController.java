package spring.gemfire.showcase.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.gemfire.showcase.account.domain.account.AccountLocation;
import spring.gemfire.showcase.account.repository.AccountRepository;
import spring.gemfire.showcase.account.repository.LocationRepository;

@RequestMapping("accountLocations")
@RequiredArgsConstructor
public class AccountLocationController {
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final String validZipRegEx = "^\\d{5}(?:[-\\s]\\d{4})?$";

    @PostMapping
    public void save(@RequestBody AccountLocation accountLocation) {

        var location = accountLocation.getLocation();

        accountRepository.save(accountLocation.getAccount());

        if(!location.getZipCode().matches(validZipRegEx)) {
            throw new IllegalArgumentException("Invalid zip code "+location.getZipCode());
        }

        locationRepository.save(accountLocation.getLocation());
    }
}
