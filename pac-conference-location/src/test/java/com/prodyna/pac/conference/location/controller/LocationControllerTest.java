package com.prodyna.pac.conference.location.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.pac.conference.location.controller.mapper.LocationToLocationDtoMapper;
import com.prodyna.pac.conference.location.controller.model.Location;
import com.prodyna.pac.conference.location.error.LocationServiceError;
import com.prodyna.pac.conference.location.error.LocationServiceErrorException;
import com.prodyna.pac.conference.location.service.LocationService;
import com.prodyna.pac.conference.location.service.model.LocationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for controller.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @SpyBean
    private LocationToLocationDtoMapper mapper;

    @Test
    void testGetAllLocations() throws Exception {
        when(locationService.getAllLocations()).thenReturn(List.of(LocationDto.builder()
                .id(1L)
                .name("Location1")
                .build(), LocationDto.builder()
                .id(2L)
                .name("Location2")
                .build()));

        mockMvc.perform(get(LocationController.LOCATION_SERVICE_URI))
                .andDo(log())
                .andExpect(status().isOk());

        verify(mapper, times(2)).mapTo(any());
        verify(locationService).getAllLocations();
    }

    @Test
    void testGetLocation() throws Exception {
        when(locationService.getLocation(anyLong())).thenReturn(LocationDto.builder()
                .id(1L)
                .name("Location1")
                .build());

        mockMvc.perform((get(LocationController.LOCATION_SERVICE_URI + "{0}", 1)))
                .andDo(log())
                .andExpect(status().isOk());

        verify(mapper).mapTo(any());
        verify(locationService).getLocation(anyLong());
    }

    @Test
    void testGetLocation_exceptionHandling() throws Exception {
        when(locationService.getLocation(anyLong())).thenThrow(new LocationServiceErrorException(LocationServiceError.UNKNOWN_SERVICE_ERROR, "Unknown error occurred!"));

        mockMvc.perform((get(LocationController.LOCATION_SERVICE_URI + "{0}", 1)))
                .andDo(log())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("9000"))
                .andExpect(jsonPath("$.details").value("Unknown error occurred!"));

        verify(locationService).getLocation(anyLong());
    }

    @Test
    void testAddLocation() throws Exception {
        when(locationService.addLocation(any())).thenReturn(LocationDto.builder()
                .id(1L)
                .name("Location1")
                .build());

        mockMvc.perform(post(LocationController.LOCATION_SERVICE_URI).contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(Location.builder()
                        .name("Location1")
                        .build())).characterEncoding(StandardCharsets.UTF_8.displayName())
        ).andDo(log()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1"));

        verify(mapper).mapTo(any());
        verify(mapper).mapFrom(any());
        verify(locationService).addLocation(any());
    }

    @Test
    void testUpdateLocation() throws Exception {
        when(locationService.updateLocation(anyLong(), any())).thenReturn(LocationDto.builder()
                .id(1L)
                .name("Location1a")
                .build());

        mockMvc.perform(put(LocationController.LOCATION_SERVICE_URI + "{0}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(Location.builder()
                        .id(1L)
                        .name("Location1a")
                        .build()))
                .characterEncoding(StandardCharsets.UTF_8.displayName())
        ).andDo(log()).andExpect(status().isOk());

        verify(mapper).mapFrom(any());
        verify(mapper).mapTo(any());
        verify(locationService).updateLocation(anyLong(), any());
    }

    @Test
    void testDeleteLocation() throws Exception {
        when(locationService.deleteLocation(anyLong())).thenReturn(LocationDto.builder()
                .id(1L)
                .name("Location1a")
                .build());

        mockMvc.perform(delete(LocationController.LOCATION_SERVICE_URI + "{0}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName()))
                .andDo(log())
                .andExpect(status().isOk());

        verify(mapper).mapTo(any());
        verify(locationService).deleteLocation(anyLong());
    }

    private String toJsonString(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
}
