package com.example.hubject.services.contracts;

import com.example.hubject.models.ChargingStation;

import java.util.List;

public interface ChargingStationService {

    List<ChargingStation> getAllChargingStations();

    ChargingStation getChargingStationById(int chargingStationId);

    List<ChargingStation> getChargingStationByZipcode(int zipcode);

    ChargingStation getChargingStationByGeolocation(double latitude, double longitude);

    void addChargingStation(ChargingStation chargingStation);

    void updateChargingStation(ChargingStation chargingStation);

    void deleteChargingStation(ChargingStation chargingStation);


}
