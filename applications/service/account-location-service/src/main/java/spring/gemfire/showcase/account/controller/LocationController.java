package spring.gemfire.showcase.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.gemfire.showcase.account.domain.account.Location;
import spring.gemfire.showcase.account.repository.LocationRepository;

@RestController
@RequestMapping("locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationRepository locationRepository;

    @PostMapping("location")
    public void save(@RequestBody Location location) {
        locationRepository.save(location);
    }

    @GetMapping("location/{id}")
    public Location findById(@PathVariable String id) {
        return locationRepository.findById(id).orElse(null);
    }
}
