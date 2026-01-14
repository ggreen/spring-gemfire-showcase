package spring.gemfire.showcase.account.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.gemfire.showcase.account.domain.account.Location;
import spring.gemfire.showcase.account.repository.LocationRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    private final Location location = JavaBeanGeneratorCreator.of(Location.class).create();
    private LocationController subject;
    @Mock
    private LocationRepository repository;

    @BeforeEach
    void setUp() {
        subject = new LocationController(repository);
    }

    @Test
    void saveLocation() {

        subject.save(location);

        verify(repository).save(any(Location.class));
    }

    @Test
    void findLocation() {
        when(repository.findById(anyString())).thenReturn(Optional.of(location));
        var actual = subject.findById(location.getId());
        assertThat(actual).isNotNull();
    }
}