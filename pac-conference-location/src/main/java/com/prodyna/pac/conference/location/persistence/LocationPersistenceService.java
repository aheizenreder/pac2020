package com.prodyna.pac.conference.location.persistence;

import com.prodyna.pac.conference.location.service.model.LocationDto;

import java.util.List;

/**
 * Interface definition of persistence Service for Location.
 */
public interface LocationPersistenceService {
    /**
     * reads and delivers all persisted Locations.
     *
     * @return List with all persisted Locations.
     */
    List<LocationDto> getAllLocations();

    /**
     * reads and delivers data for a location with provided id.
     *
     * @param id id of a Location to read
     * @return Location data.
     */
    LocationDto getLocation(Long id);

    /**
     * persists new Location.
     *
     * @param newLocation Location object to persist. The method returns updated Location object with data after persistence.
     * @return updated version of Location object.
     */
    LocationDto addLocation(LocationDto newLocation);

    /**
     * updates an existing Location object, identified by id.
     *
     * @param id        id of object to update.
     * @param updateDto Location data to update.
     * @return updated version of Location object.
     */
    LocationDto updateLocation(Long id, LocationDto updateDto);

    /**
     * deletes a location identified by id.
     *
     * @param id id of a Location to delete.
     * @return deleted Location data.
     */
    LocationDto deleteLocation(Long id);

}
