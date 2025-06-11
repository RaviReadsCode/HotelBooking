package com.booking.hotelapi.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booking.hotelapi.entity.City;
import com.booking.hotelapi.entity.Hotel;
import com.booking.hotelapi.exception.CityNotFoundException;
import com.booking.hotelapi.exception.HotelNotFoundException;
import com.booking.hotelapi.exception.MissingDataException;
import com.booking.hotelapi.repository.CityRepository;
import com.booking.hotelapi.repository.HotelRepository;
import com.booking.hotelapi.service.HotelService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Hotel getHotelById(Integer id) {
        if (id == null) {
            throw new MissingDataException("Hotel ID is missing");
        }
        return hotelRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with ID: " + id));
    }


    @Override
    public void deleteHotelById(Integer id) {
        Hotel hotel = getHotelById(id);
        hotel.setDeleted(true);
        hotelRepository.save(hotel);
    }

//    @Override
//    public List<Hotel> getHotelsSortedByDistance(Integer cityId) {
//        City city = cityRepository.findById(cityId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
//
//        List<Hotel> hotels = hotelRepository.findAllByCityId(cityId);
//
//        return hotels.stream()
//                .sorted(Comparator.comparingDouble(h -> haversine(
//                        city.getCityCentreLatitude(), city.getCityCentreLongitude(),
//                        h.getLatitude(), h.getLongitude())))
//                .collect(Collectors.toList());
//    }
    
    @Override
    public List<Hotel> getTop3HotelsSortedByDistance(Integer cityId) {
        if (cityId == null) {
            throw new MissingDataException("City ID is missing");
        }

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException("City not found with ID: " + cityId));

        List<Hotel> hotels = hotelRepository.findAllByCityId(cityId);
        if (hotels == null || hotels.isEmpty()) {
            throw new HotelNotFoundException("No hotels found for city ID: " + cityId);
        }

        return hotels.stream()
                .sorted(Comparator.comparingDouble(h -> haversine(
                        city.getCityCentreLatitude(), city.getCityCentreLongitude(),
                        h.getLatitude(), h.getLongitude())))
                .limit(3)
                .collect(Collectors.toList());
    }
 


    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in KM
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
