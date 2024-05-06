package com.example.hubject.models.dto;

import com.example.hubject.models.Zipcode;

public class ChargingStationDto {

    private double latitude;

    private double longitude;
    private Zipcode zipcode;

    public ChargingStationDto() {
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Zipcode getZipcode() {
        return zipcode;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }
}
