package com.prodyna.pac.conference.location.controller;

import com.prodyna.pac.conference.location.controller.mapper.LocationToLocationDtoMapper;
import com.prodyna.pac.conference.location.controller.model.Location;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping(LocationController.LOCATION_SERVICE_URI)
@AllArgsConstructor(onConstructor = @__({@Inject}))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationController {

    /**
     * URI of the location service
     */
    public static final String LOCATION_SERVICE_URI = "/";

    LocationService locationService;

    LocationToLocationDtoMapper mapper;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<@Valid Location> getAllLocations() {
        log.debug("getAllLocations() ...");
        return locationService.getAllLocations().stream().map(l -> mapper.mapTo(l)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public @Valid Location getLocationById(@PathVariable Long id) {
        log.debug("getLocation({})", id);
        LocationDto locationDto = locationService.getLocation(id);
        log.debug("location: {}", locationDto);
        return mapper.mapTo(locationDto);
    }

    @PostMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public @Valid Location addNewLocation(@RequestBody Location location) {
        log.debug("addNewLocation: {}", location);
        LocationDto locationDto = locationService.addLocation(mapper.mapFrom(location));
        log.debug("added location: {}", locationDto);
        return mapper.mapTo(locationDto);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public @Valid Location updateLocation(@RequestBody @Valid Location location, @PathVariable @NotNull Long id) {
        log.debug("updateLocation id: {}, location: {}", id, location);
        LocationDto updatedLocationDto = locationService.updateLocation(id, mapper.mapFrom(location));
        log.debug("updated location: {}", updatedLocationDto);
        return mapper.mapTo(updatedLocationDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public @Valid Location deleteLocation(@PathVariable @NotNull Long id) {
        log.debug("deleteLocation(): {}", id);
        LocationDto deletedLocationDto = locationService.deleteLocation(id);
        log.debug("deleted location: {}", deletedLocationDto);
        return mapper.mapTo(deletedLocationDto);
    }
}
