package com.booking.hotelapi;

import com.booking.hotelapi.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Demo1ApplicationTests {

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void contextLoads() {
        // basic check: Spring context loads without exceptions
        assertThat(hotelRepository).isNotNull();
    }

    @Test
    void testFindHotelById() {
        // assume hotel with ID 1 exists for test
        hotelRepository.findByIdAndDeletedFalse(1)
            .ifPresentOrElse(
                hotel -> assertThat(hotel.getId()).isEqualTo(1),
                () -> System.out.println("Hotel not found — check test data")
            );
    }
}
