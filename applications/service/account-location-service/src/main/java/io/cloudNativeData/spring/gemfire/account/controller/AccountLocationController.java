package io.cloudNativeData.spring.gemfire.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.cloudNativeData.spring.gemfire.account.domain.account.AccountLocation;
import io.cloudNativeData.spring.gemfire.account.repository.AccountRepository;
import io.cloudNativeData.spring.gemfire.account.repository.LocationRepository;

@Component
@RestController
@RequestMapping("accountLocations")
@RequiredArgsConstructor
public class AccountLocationController {
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;
    private final static String validZipRegEx = "^\\d{5}(?:[-\\s]\\d{4})?$";


    @PostMapping
    @Transactional
    public void save(@RequestBody AccountLocation accountLocation) {

        var location = accountLocation.getLocation();
        location.setId(accountLocation.getAccount().getId());

        accountRepository.save(accountLocation.getAccount());

        if(!location.getZipCode().matches(validZipRegEx)) {
            throw new IllegalArgumentException("Invalid zip code "+location.getZipCode());
        }

        locationRepository.save(accountLocation.getLocation());
    }

    @GetMapping("{id}")
    public AccountLocation findById(@PathVariable  String id) {

        return AccountLocation.builder()
                .location(locationRepository.findById(id).orElse(null))
                .account(accountRepository.findById(id).orElse(null))
                .build();
    }
}
