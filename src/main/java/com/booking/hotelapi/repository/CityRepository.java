// CityRepository.java
package com.booking.hotelapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.hotelapi.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
