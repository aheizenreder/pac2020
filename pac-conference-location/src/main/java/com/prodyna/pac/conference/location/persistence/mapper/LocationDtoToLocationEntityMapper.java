package com.prodyna.pac.conference.location.persistence.mapper;

import com.prodyna.pac.conference.location.persistence.LocationEntity;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Mapper from {@link LocationDto} to {@link LocationEntity} and back.
 */
@Component
public class LocationDtoToLocationEntityMapper {

    private final BoundMapperFacade<LocationDto, LocationEntity> mapper;

    @Inject
    public LocationDtoToLocationEntityMapper(MapperFactory mapperFactory) {
        mapper = mapperFactory.getMapperFacade(LocationDto.class, LocationEntity.class);
    }

    /**
     * maps from an instance of {@link LocationDto} to {@link LocationEntity}.
     *
     * @param locationDto an instance of {@link LocationDto} to map to {@link LocationEntity}.
     * @return result of mapping.
     */
    public LocationEntity mapFrom(LocationDto locationDto) {
        return mapper.map(locationDto);
    }

    /**
     * maps from {@link LocationEntity} to {@link LocationDto}.
     *
     * @param locationEntity an instance of {@link LocationEntity} to map.
     * @return result of mapping.
     */
    public LocationDto mapTo(LocationEntity locationEntity) {
        return mapper.mapReverse(locationEntity);
    }
}
