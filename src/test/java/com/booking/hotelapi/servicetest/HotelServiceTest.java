package com.booking.hotelapi.servicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.hotelapi.entity.City;
import com.booking.hotelapi.entity.Hotel;
import com.booking.hotelapi.exception.CityNotFoundException;
import com.booking.hotelapi.exception.HotelNotFoundException;
import com.booking.hotelapi.exception.MissingDataException;
import com.booking.hotelapi.repository.CityRepository;
import com.booking.hotelapi.repository.HotelRepository;
import com.booking.hotelapi.service.impl.HotelServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    
    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

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
    void getHotelById_validId_returnsHotel() {
        Hotel hotel = new Hotel(5, "Hotel A", 8, 52.376, 4.900 , "RAVIRAJ PALACE" , false, testCity);
        hotel.setId(5);
        hotel.setDeleted(false);

        when(hotelRepository.findByIdAndDeletedFalse(5)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(5);

        assertNotNull(result);
        assertEquals(5, result.getId());
    }

    @Test
    void getHotelById_nullId_throwsException() {
        MissingDataException exception = assertThrows(MissingDataException.class, () -> hotelService.getHotelById(null));
        assertEquals("Hotel ID is missing", exception.getMessage());
    }

    @Test
    void getHotelById_notFound_throwsException() {
        when(hotelRepository.findByIdAndDeletedFalse(100)).thenReturn(Optional.empty());

        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(100));
        assertTrue(exception.getMessage().contains("Hotel not found"));
    }

    @Test
    void getTop3HotelsSortedByDistance_validCity_returnsHotels() {
        City city = new City(8,"Amsterdam", 12.00, 77.00);
        city.setId(8);
        city.setCityCentreLatitude(12.00);
        city.setCityCentreLongitude(77.00);

        Hotel h1 = new Hotel(1, "Hotel A", 8.0, 12.01, 77.01, "Addr A", false, city);
        Hotel h2 = new Hotel(2, "Hotel B", 9.0, 12.02, 77.02, "Addr B", false, city);
        Hotel h3 = new Hotel(3, "Hotel C", 9.5, 12.03, 77.03, "Addr C", false, city);
        Hotel h4 = new Hotel(4, "Hotel D", 8.2, 12.04, 77.04, "Addr D", false, city);

        when(cityRepository.findById(8)).thenReturn(Optional.of(city));
        when(hotelRepository.findAllByCityId(8)).thenReturn(Arrays.asList(h1, h2, h3, h4));

        List<Hotel> top3 = hotelService.getTop3HotelsSortedByDistance(8);

        assertEquals(3, top3.size());
        assertTrue(top3.contains(h1));
    }

    @Test
    void getTop3HotelsSortedByDistance_nullCityId_throwsException() {
        MissingDataException exception = assertThrows(MissingDataException.class, () -> hotelService.getTop3HotelsSortedByDistance(null));
        assertEquals("City ID is missing", exception.getMessage());
    }

    @Test
    void getTop3HotelsSortedByDistance_cityNotFound_throwsException() {
        when(cityRepository.findById(200)).thenReturn(Optional.empty());

        CityNotFoundException exception = assertThrows(CityNotFoundException.class, () -> hotelService.getTop3HotelsSortedByDistance(200));
        assertEquals("City not found with ID: 200", exception.getMessage());
    }

    @Test
    void getTop3HotelsSortedByDistance_noHotels_throwsException() {
        City city = new City(7,"Amsterdam", 52.36878, 4.903303);
        city.setId(7);
        city.setCityCentreLatitude(52.36878);
        city.setCityCentreLongitude(4.903303);

        when(cityRepository.findById(7)).thenReturn(Optional.of(city));
        when(hotelRepository.findAllByCityId(7)).thenReturn(List.of());

        HotelNotFoundException exception = assertThrows(HotelNotFoundException.class, () -> hotelService.getTop3HotelsSortedByDistance(7));
        assertEquals("No hotels found for city ID: 7", exception.getMessage());
    }
}
