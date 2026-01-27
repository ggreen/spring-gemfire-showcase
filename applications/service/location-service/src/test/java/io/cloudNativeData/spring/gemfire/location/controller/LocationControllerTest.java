package io.cloudNativeData.spring.gemfire.location.controller;

import io.cloudNativeData.spring.gemfire.account.domain.account.Location;
import io.cloudNativeData.spring.gemfire.location.repository.LocationRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing for LocationControllerTest
 * @author gregory green
 */
@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    private LocationController subject;

    @Mock
    private LocationRepository repository;

    private Location location = JavaBeanGeneratorCreator.of(Location.class).create();

    @BeforeEach
    void setUp() {
        subject = new LocationController(repository);
    }

    @Test
    void saveLocation() {

        subject.saveLocation(location);

        verify(repository).save(any(Location.class));
    }

    @Test
    void read() {

        when(repository.findById(any())).thenReturn(Optional.of(location));

        var actual = subject.findLocation(location.getId());

        assertThat(actual).isEqualTo(location);
    }
}