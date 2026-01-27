package io.cloudNativeData.spring.gemfire.location.controller;

import io.cloudNativeData.spring.gemfire.account.domain.account.Location;
import io.cloudNativeData.spring.gemfire.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Location HTTP REST controller
 * @author gregory green
 */
@RestController
@RequestMapping("locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository repository;

    /**
     * Put location info into the repository
     * @param location the account location
     */
    @PostMapping("location")
    public void saveLocation(@RequestBody Location location) {
        repository.save(location);
    }

    /**
     * Get location info from the repository
     * @param id the location identifier
     * @return matching location or mull
     */
    @GetMapping("location/{id}")
    public Location findLocation(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }
}
