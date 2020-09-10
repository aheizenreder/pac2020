package com.prodyna.pac.conference.location.service.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Data transfer object for a Location.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    Long id;

    String name;
}
