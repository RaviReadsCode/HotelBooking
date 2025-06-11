// SearchController.java
package com.booking.hotelapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booking.hotelapi.entity.Hotel;
import com.booking.hotelapi.service.HotelService;



import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Hotel>> getTop3HotelsSorted(@PathVariable Integer id, @RequestParam(required = false) String sortBy) {
        if ("distance".equalsIgnoreCase(sortBy)) {
            return ResponseEntity.ok(hotelService.getTop3HotelsSortedByDistance(id));
        }
        return ResponseEntity.badRequest().build();
    }
    
//    @GetMapping("/{id}")
//    public ResponseEntity<List<Hotel>> getTop3HotelsSorted(@PathVariable Integer id) {
//        return ResponseEntity.ok(hotelService.getTop3HotelsSortedByDistance(id));
//    }
}
