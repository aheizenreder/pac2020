package com.prodyna.pac.conference.location.persistence;

import com.prodyna.pac.conference.location.error.LocationServiceError;
import com.prodyna.pac.conference.location.error.LocationServiceErrorException;
import com.prodyna.pac.conference.location.persistence.mapper.LocationDtoToLocationEntityMapper;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Inject))
public class LocationPersistenceServiceImpl implements LocationPersistenceService {
    LocationRepository locationRepository;
    LocationDtoToLocationEntityMapper mapper;

    /**
     * reads and delivers all persisted Locations.
     *
     * @return List with all persisted Locations.
     */
    @Override
    public List<LocationDto> getAllLocations() {
        return StreamSupport.stream(locationRepository.findAll().spliterator(), false)
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    /**
     * reads and delivers data for a location with provided id.
     *
     * @param id id of a Location to read
     * @return Location data.
     */
    @Override
    public LocationDto getLocation(Long id) {
        log.debug("get location from database for id {}", id);
        Optional<LocationEntity> entityOptional = locationRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.warn("Location for id {} not found!", id);
            throw new LocationServiceErrorException(LocationServiceError.NO_LOCATION_FOUND, "Location with id = " + id + " not found!");
        }
        log.debug("Location from database: {}", entityOptional.get());
        return mapper.mapTo(entityOptional.get());
    }

    /**
     * persists new Location.
     *
     * @param newLocation Location object to persist. The method returns updated Location object with data after persistence.
     * @return updated version of Location object.
     */
    @Override
    public LocationDto addLocation(LocationDto newLocation) {
        LocationEntity locationEntity;
        log.debug("add new location: {}", newLocation);
        try {
            locationEntity = locationRepository.save(mapper.mapFrom(newLocation));
        } catch (Exception e) {
            log.error("Location cannot be saved!", e);
            throw new LocationServiceErrorException(LocationServiceError.LOCATION_NOT_SAVED, e);
        }
        log.debug("Location after persisting: {}", locationEntity);
        return mapper.mapTo(locationEntity);
    }

    /**
     * updates an existing Location object, identified by id.
     *
     * @param id        id of object to update.
     * @param updateDto Location data to update.
     * @return updated version of Location object.
     */
    @Override
    public LocationDto updateLocation(Long id, LocationDto updateDto) {
        LocationEntity updatedLocation;
        log.debug("Update location with id {} with new value {} ...", id, updateDto);
        updateDto.setId(id);
        try {
            updatedLocation = locationRepository.save(mapper.mapFrom(updateDto));
        } catch (Exception e) {
            log.error("Location cannot be updated!", e);
            throw new LocationServiceErrorException(LocationServiceError.LOCATION_NOT_UPDATED, e);
        }
        log.debug("Updated location: {}", updatedLocation);
        return mapper.mapTo(updatedLocation);
    }

    /**
     * deletes a location identified by id.
     *
     * @param id id of a Location to delete.
     * @return deleted Location data.
     */
    @Override
    public LocationDto deleteLocation(Long id) {
        log.debug("Delete location with id {} ...", id);
        LocationDto location = getLocation(id);
        try {
            locationRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error by delete Location with id " + id + "!", e);
            throw new LocationServiceErrorException(LocationServiceError.LOCATION_NOT_DELETED, e);
        }
        log.debug("Deleted location: {}", location);
        return location;
    }
}
