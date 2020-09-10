package com.prodyna.pac.conference.location.controller.mapper;

import com.prodyna.pac.conference.location.controller.model.Location;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Mapper from @{@link com.prodyna.pac.conference.location.controller.model.Location} to @{@link com.prodyna.pac.conference.location.service.model.LocationDto}
 * and back.
 */
@Component
public class LocationToLocationDtoMapper {

    private BoundMapperFacade<Location, LocationDto> mapper;

    @Inject
    public LocationToLocationDtoMapper(MapperFactory mapperFactory) {
        mapper = mapperFactory.getMapperFacade(Location.class, LocationDto.class);
    }

    /**
     * maps from an instance of {@link Location} to {@link LocationDto}.
     *
     * @param location an instance of {@link Location} to map to {@link LocationDto}.
     * @return result of mapping.
     */
    public LocationDto mapFrom(Location location) {
        return mapper.map(location);
    }

    /**
     * maps from an instance of {@link LocationDto} to {@link Location}.
     *
     * @param locationDto an instance of {@link LocationDto} to map to {@link Location}.
     * @return result of mapping.
     */
    public Location mapTo(LocationDto locationDto) {
        return mapper.mapReverse(locationDto);
    }
}
