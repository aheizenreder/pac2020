package com.prodyna.pac.conference.location.persistence;

import com.prodyna.pac.conference.location.error.LocationServiceError;
import com.prodyna.pac.conference.location.error.LocationServiceErrorException;
import com.prodyna.pac.conference.location.persistence.mapper.LocationDtoToLocationEntityMapper;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit test for implementation of {@link LocationPersistenceService}.
 */
@ExtendWith(MockitoExtension.class)
class LocationPersistenceServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Spy
    private LocationDtoToLocationEntityMapper mapper = new LocationDtoToLocationEntityMapper(new DefaultMapperFactory.Builder().build());
    ;

    private LocationPersistenceServiceImpl service;

    @BeforeEach
    void setUp() {
        assertThat(locationRepository).isNotNull();
        service = new LocationPersistenceServiceImpl(locationRepository, mapper);
    }

    @Test
    void testGetAllLocations_Success() {
        when(locationRepository.findAll()).thenReturn(List.of(LocationEntity.builder()
                .id(1L)
                .name("Location1")
                .build(), LocationEntity.builder()
                .id(2L)
                .name("Location2")
                .build()));
        List<LocationDto> allLocations = service.getAllLocations();

        assertThat(allLocations).isNotNull().isNotEmpty().hasSize(2);

        verify(locationRepository).findAll();
        verify(mapper, times(2)).mapTo(any(LocationEntity.class));
    }

    @Test
    void testGetAllLocations_NoLocations() {
        when(locationRepository.findAll()).thenReturn(List.of());

        List<LocationDto> allLocations = service.getAllLocations();

        assertThat(allLocations).isNotNull().isEmpty();

        verify(locationRepository).findAll();
        verify(mapper, never()).mapTo(any(LocationEntity.class));
    }

    @Test
    void testGetLocation_Success() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(LocationEntity.builder()
                .id(1L)
                .name("Location1")
                .build()));
        LocationDto location = service.getLocation(1L);

        assertThat(location).isNotNull();
        assertThat(location.getId()).isEqualTo(1L);
        assertThat(location.getName()).isEqualTo("Location1");

        verify(locationRepository).findById(anyLong());
        verify(mapper).mapTo(any(LocationEntity.class));
    }

    @Test
    void testGetLocation() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        LocationServiceErrorException expectedException = catchThrowableOfType(() -> service.getLocation(1L), LocationServiceErrorException.class);

        assertThat(expectedException.getError()).isEqualTo(LocationServiceError.NO_LOCATION_FOUND);
        assertThat(expectedException.getDetails()).isEqualTo("Location with id = 1 not found!");

        verify(locationRepository).findById(anyLong());
        verify(mapper, never()).mapTo(any(LocationEntity.class));
    }

    @Test
    void testAddLocation_Success() {
        when(locationRepository.save(any(LocationEntity.class))).thenReturn(LocationEntity.builder()
                .id(1L)
                .name("Location1")
                .build());

        LocationDto location = service.addLocation(LocationDto.builder()
                .name("Location1")
                .build());

        assertThat(location).isNotNull();
        assertThat(location.getId()).isEqualTo(1L);
        assertThat(location.getName()).isEqualTo("Location1");

        verify(locationRepository).save(any(LocationEntity.class));
        verify(mapper).mapFrom(any(LocationDto.class));
        verify(mapper).mapTo(any(LocationEntity.class));
    }

    @Test
    void updateLocation() {
        when(locationRepository.save(any(LocationEntity.class))).thenReturn(LocationEntity.builder()
                .id(1L)
                .name("Location1a")
                .build());

        LocationDto location = service.updateLocation(1L, LocationDto.builder()
                .name("Location1a")
                .build());

        assertThat(location).isNotNull();
        assertThat(location.getId()).isEqualTo(1L);
        assertThat(location.getName()).isEqualTo("Location1a");

        verify(locationRepository).save(any(LocationEntity.class));
        verify(mapper).mapFrom(any(LocationDto.class));
        verify(mapper).mapTo(any(LocationEntity.class));
    }

    @Test
    void deleteLocation() {
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(LocationEntity.builder()
                .id(1L)
                .name("Location1a")
                .build()));
        doNothing().when(locationRepository).deleteById(anyLong());

        LocationDto location = service.deleteLocation(1L);

        assertThat(location).isNotNull();
        assertThat(location.getId()).isEqualTo(1L);
        assertThat(location.getName()).isEqualTo("Location1a");

        verify(locationRepository).findById(anyLong());
        verify(mapper).mapTo(any(LocationEntity.class));

    }

}