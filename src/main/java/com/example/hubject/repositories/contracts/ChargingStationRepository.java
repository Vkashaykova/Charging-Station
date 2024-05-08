package com.example.hubject.repositories.contracts;

import com.example.hubject.models.ChargingStation;

import java.util.List;
import java.util.Optional;

public interface ChargingStationRepository {
    List<ChargingStation> getAllChargingStations();

    Optional<ChargingStation> getChargingStationById(int chargingStationId);

    Optional<List<ChargingStation>> getChargingStationByZipcode(int zipcode);

    Optional<ChargingStation> getChargingStationByGeolocation(double latitude, double longitude);

    void addChargingStation(ChargingStation chargingStation);

    void updateChargingStation(ChargingStation chargingStation);

    void deleteChargingStation(ChargingStation chargingStation);
}
