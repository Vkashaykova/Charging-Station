package com.example.hubject;

import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;

public class helpers {
    public static ChargingStation createMockChargingStation() {
        ChargingStation mockChargingStation = new ChargingStation();
        mockChargingStation.setId(1);
        mockChargingStation.setLatitude(42.408400);
        mockChargingStation.setLongitude(-71.053700);
        Zipcode zipcode = new Zipcode();
        zipcode.setId(1);
        zipcode.setZipcode(22155);
        mockChargingStation.setZipcode(zipcode);


        return mockChargingStation;
    }

    public static  Zipcode createMockZipcode() {
        Zipcode zipcode = new Zipcode();
        zipcode.setId(1);
        zipcode.setZipcode(12345);
        return zipcode;
    }
}
