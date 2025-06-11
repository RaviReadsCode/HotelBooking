// HotelRepository.java
package com.booking.hotelapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.Hotel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Optional<Hotel> findByIdAndDeletedFalse(Integer id);

    @Query("SELECT h FROM Hotel h WHERE h.city.id = :cityId")
    List<Hotel> findAllByCityId(Integer cityId);

}
