package com.booking.hotelapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double cityCentreLatitude;
    private double cityCentreLongitude;
    
    public City() {};

    public City(Integer id, String name, double cityCentreLatitude, double cityCentreLongitude) {
		super();
		this.id = id;
		this.name = name;
		this.cityCentreLatitude = cityCentreLatitude;
		this.cityCentreLongitude = cityCentreLongitude;
	}	

	// Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public double getCityCentreLatitude() {
		return cityCentreLatitude;
	}

	public void setCityCentreLatitude(double cityCentreLatitude) {
		this.cityCentreLatitude = cityCentreLatitude;
	}

	public double getCityCentreLongitude() {
		return cityCentreLongitude;
	}

	public void setCityCentreLongitude(double cityCentreLongitude) {
		this.cityCentreLongitude = cityCentreLongitude;
	}

	    
}
