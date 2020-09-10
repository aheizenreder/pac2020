package com.prodyna.pac.conference.location.controller.mapper;

import com.prodyna.pac.conference.location.controller.model.Location;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for mapper.
 */
class LocationToLocationDtoMapperTest {

    private LocationToLocationDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new LocationToLocationDtoMapper(new DefaultMapperFactory.Builder().build());
    }

    @Test
    void mapFrom() {
        Location location = Location.builder()
                .id(1L)
                .name("Location1")
                .build();

        LocationDto mappingResult = mapper.mapFrom(location);

        assertThat(mappingResult).isNotNull();
        assertThat(mappingResult.getId()).isEqualTo(location.getId());
        assertThat(mappingResult.getName()).isEqualTo(location.getName());

    }

    @Test
    void mapTo() {
        LocationDto locationDto = LocationDto.builder()
                .id(1L)
                .name("Location1")
                .build();
        Location mappingResult = mapper.mapTo(locationDto);
        assertThat(mappingResult).isNotNull();
        assertThat(mappingResult.getId()).isEqualTo(locationDto.getId());
        assertThat(mappingResult.getName()).isEqualTo(locationDto.getName());
    }
}