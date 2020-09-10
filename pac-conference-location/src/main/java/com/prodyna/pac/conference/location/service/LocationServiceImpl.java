package com.prodyna.pac.conference.location.service;

import com.prodyna.pac.conference.location.persistence.LocationPersistenceService;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of business service to manage Location.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class LocationServiceImpl implements LocationService {
    LocationPersistenceService persistenceService;

    /**
     * get all locations.
     *
     * @return list with all locations.
     */
    @Override
    public List<LocationDto> getAllLocations() {
        return persistenceService.getAllLocations();
    }

    /**
     * get a location by id.
     *
     * @param id to identify a location.
     * @return location identified by id.
     */
    @Override
    public LocationDto getLocation(Long id) {
        return persistenceService.getLocation(id);
    }

    /**
     * adds new location.
     *
     * @param newLocation location to add.
     * @return location after persistence. Normally the id is set after from database.
     */
    @Override
    public LocationDto addLocation(LocationDto newLocation) {
        return persistenceService.addLocation(newLocation);
    }

    /**
     * updates a location by id.
     *
     * @param id        id of the location to update.
     * @param updateDto new state of location to store.
     * @return location after update.
     */
    @Override
    public LocationDto updateLocation(Long id, LocationDto updateDto) {
        return persistenceService.updateLocation(id, updateDto);
    }

    /**
     * deletes a location identified by id.
     *
     * @param id id of location to
     * @return deleted location.
     */
    @Override
    public LocationDto deleteLocation(Long id) {
        return persistenceService.deleteLocation(id);
    }
}
