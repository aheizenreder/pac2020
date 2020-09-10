package com.prodyna.pac.conference.location.controller.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class is the representation of a location o Web layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    @NotNull
    @Min(value = 0)
    Long id;

    @NotBlank
    String name;
}
