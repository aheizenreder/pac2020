package com.prodyna.pac.conference.location.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Unit Test to test the repository for accessing location data.
 */
@DataNeo4jTest
class LocationRepositoryIT {

    @Inject
    LocationRepository repository;

    @Test
    void testSave() {
        LocationEntity savedLocation = repository.save(LocationEntity.builder().name("Location1").build());

        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getId()).isNotNull();
        assertThat(savedLocation.getName()).isNotBlank().isEqualTo("Location1");
    }

    @Test
    void testFindById() {
        LocationEntity savedLocation = repository.save(LocationEntity.builder().name("Location1").build());

        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getId()).isNotNull();
        assertThat(savedLocation.getName()).isNotBlank().isEqualTo("Location1");

        Optional<LocationEntity> readLocation = repository.findById(savedLocation.getId());

        assertThat(readLocation).isPresent();
        assertThat(readLocation.get()).isEqualTo(savedLocation);
    }

    @Test
    void testFindAll() {
        LocationEntity savedLocation = repository.save(LocationEntity.builder().name("Location1").build());
        LocationEntity savedLocation2 = repository.save(LocationEntity.builder().name("Location2").build());

        Iterable<LocationEntity> allLocations = repository.findAll();

        assertThat(allLocations).isNotNull().isNotEmpty().hasSize(2).containsExactlyInAnyOrder(savedLocation, savedLocation2);
    }

    @Test
    void testUpdate() {
        LocationEntity savedLocation = repository.save(LocationEntity.builder().name("Location1").build());

        savedLocation.setName("Location1a");
        LocationEntity updatedLocation = repository.save(savedLocation);

        assertThat(updatedLocation).isNotNull();
        assertThat(updatedLocation.getId()).isEqualTo(savedLocation.getId());
        assertThat(updatedLocation.getName()).isEqualTo("Location1a");
    }

    @Test
    void testDelete() {
        LocationEntity savedLocation = repository.save(LocationEntity.builder().name("Location1").build());

        repository.deleteById(savedLocation.getId());

        Optional<LocationEntity> locationAfterDeleteOptional = repository.findById(savedLocation.getId());
        assertThat(locationAfterDeleteOptional).isEmpty();
    }
}