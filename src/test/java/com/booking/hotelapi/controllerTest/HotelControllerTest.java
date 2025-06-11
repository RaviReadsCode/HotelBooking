package com.booking.hotelapi.controllerTest;

import com.booking.hotelapi.controller.HotelController;
import com.booking.hotelapi.controller.SearchController;
import com.booking.hotelapi.entity.City;
import com.booking.hotelapi.entity.Hotel;
import com.booking.hotelapi.exception.HotelNotFoundException;
import com.booking.hotelapi.service.HotelService;
import com.booking.hotelapi.service.impl.HotelServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({HotelController.class, SearchController.class})
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelServiceImpl hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    private Hotel testHotel;
    private City testCity;

    @BeforeEach
    void setUp() {
        testCity = new City(5,"Amsterdam", 52.36878, 4.903303);
        testCity.setId(1);
        
        testHotel = new Hotel(
        		6,
            "The Bank Hotel Amsterdam",
            9.2,
            2.37276,
            4.893604,
            "10 Bank Street, 10110 AB",
            false,
            testCity
        );
        testHotel.setId(10);
    }

    @Test
    void getHotelById_Success() throws Exception {
        when(hotelService.getHotelById(10)).thenReturn(testHotel);

        mockMvc.perform(get("/hotel/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("The Bank Hotel Amsterdam"))
                .andExpect(jsonPath("$.rating").value(9.2))
                .andExpect(jsonPath("$.latitude").value(2.37276))
                .andExpect(jsonPath("$.longitude").value(4.893604))
                .andExpect(jsonPath("$.address").value("10 Bank Street, 10110 AB"))
                .andExpect(jsonPath("$.city.name").value("Amsterdam"));

        verify(hotelService, times(1)).getHotelById(10);
    }

    @Test
    void getHotelById_NotFound() throws Exception {
        when(hotelService.getHotelById(999)).thenThrow(new HotelNotFoundException("Hotel with ID 999 not found"));

        mockMvc.perform(get("/hotel/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Hotel Not Found"))
                .andExpect(jsonPath("$.message").value("Hotel with ID 999 not found"));

        verify(hotelService, times(1)).getHotelById(999);
    }

    @Test
    void deleteHotelById_Success() throws Exception {
        doNothing().when(hotelService).deleteHotelById(10);

        mockMvc.perform(delete("/hotel/10"))
                .andExpect(status().isOk());

        verify(hotelService, times(1)).deleteHotelById(10);
    }

    @Test
    void deleteHotelById_NotFound() throws Exception {
        doThrow(new HotelNotFoundException("Hotel with ID 999 not found"))
                .when(hotelService).deleteHotelById(999);

        mockMvc.perform(delete("/hotel/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Hotel with ID 999 not found"));

        verify(hotelService, times(1)).deleteHotelById(999);
    }

    @Test
    void searchHotelsByDistance_Success() throws Exception {
        Hotel hotel1 = new Hotel(5, "Hotel A", 8, 52.376, 4.900 , "Address A" , false, testCity);
        hotel1.setId(1);
        Hotel hotel2 = new Hotel(6,"Hotel B", 8.5, 52.375, 4.905 , "Address B", false, testCity);
        hotel2.setId(2);
        Hotel hotel3 = new Hotel(6,"Hotel C", 9.0, 52.365, 4.895, "Address C", false, testCity);
        hotel3.setId(3);

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2, hotel3);
        when(hotelService.getTop3HotelsSortedByDistance(1)).thenReturn(hotels);

        mockMvc.perform(get("/search/1?sortBy=distance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));

        verify(hotelService, times(1)).getTop3HotelsSortedByDistance(1);
    }

    @Test
    void searchHotelsByDistance_InvalidSortBy() throws Exception {
        mockMvc.perform(get("/search/1?sortBy=price"))
                .andExpect(status().isBadRequest());

        verify(hotelService, never()).getTop3HotelsSortedByDistance(any());
    }

    @Test
    void searchHotelsByDistance_NoHotelsFound() throws Exception {
        when(hotelService.getTop3HotelsSortedByDistance(999))
                .thenThrow(new HotelNotFoundException("No hotels found for city ID 999"));

        mockMvc.perform(get("/search/999?sortBy=distance"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No hotels found for city ID 999"));

        verify(hotelService, times(1)).getTop3HotelsSortedByDistance(999);
    }
}