package com.prodyna.pac.conference.location.service;

import com.prodyna.pac.conference.location.service.model.LocationDto;

import java.util.List;

/**
 * Interface for business service
 */
public interface LocationService {
    /**
     * get all locations.
     *
     * @return list with all locations.
     */
    List<LocationDto> getAllLocations();

    /**
     * get a location by id.
     *
     * @param id to identify a location.
     * @return location identified by id.
     */
    LocationDto getLocation(Long id);

    /**
     * adds new location.
     *
     * @param newLocation location to add.
     * @return location after persistence. Normally the id is set after from database.
     */
    LocationDto addLocation(LocationDto newLocation);

    /**
     * updates a location by id.
     *
     * @param id        id of the location to update.
     * @param updateDto new state of location to store.
     * @return location after update.
     */
    LocationDto updateLocation(Long id, LocationDto updateDto);

    /**
     * deletes a location identified by id.
     *
     * @param id id of location to
     * @return deleted location.
     */
    LocationDto deleteLocation(Long id);
}
