package com.prodyna.pac.conference.location.persistence.mapper;

import com.prodyna.pac.conference.location.persistence.LocationEntity;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * unit test to test {@link LocationDtoToLocationEntityMapper}.
 */
class LocationDtoToLocationEntityMapperTest {

    private LocationDtoToLocationEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new LocationDtoToLocationEntityMapper(new DefaultMapperFactory.Builder().build());
    }

    @Test
    void test_mapFrom() {
        LocationDto locationDto = LocationDto.builder()
                .id(1L)
                .name("Location1")
                .build();

        LocationEntity locationEntity = mapper.mapFrom(locationDto);

        assertThat(locationEntity).isNotNull();
        assertThat(locationEntity.getId()).isEqualTo(locationDto.getId());
        assertThat(locationEntity.getName()).isEqualTo(locationDto.getName());
    }

    @Test
    void test_mapTo() {
        LocationEntity locationEntity = LocationEntity.builder()
                .id(1L)
                .name("Location1")
                .build();

        LocationDto locationDto = mapper.mapTo(locationEntity);

        assertThat(locationDto).isNotNull();
        assertThat(locationDto.getId()).isEqualTo(locationEntity.getId());
        assertThat(locationDto.getName()).isEqualTo(locationEntity.getName());
    }
}