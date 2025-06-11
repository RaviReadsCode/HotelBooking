package com.booking.hotelapi.service;


import java.util.List;

import com.booking.hotelapi.entity.Hotel;

public interface HotelService {

    Hotel getHotelById(Integer id);

    void deleteHotelById(Integer id);

    //List<Hotel> getHotelsSortedByDistance(Integer cityId);
    
    List<Hotel> getTop3HotelsSortedByDistance(Integer cityId);


}