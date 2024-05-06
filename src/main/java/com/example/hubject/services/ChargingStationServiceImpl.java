package com.example.hubject.services;

import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import com.example.hubject.services.contracts.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingStationServiceImpl implements ChargingStationService {
    private final ChargingStationRepository chargingStationRepository;

    @Autowired
    public ChargingStationServiceImpl(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }


    @Override
    public List<ChargingStation> getAllChargingStations() {
        return chargingStationRepository.getAllChargingStations();
    }

    @Override
    public ChargingStation getChargingStationById(int chargingStationId) {
        return chargingStationRepository.getChargingStationById(chargingStationId)
                .orElseThrow(() -> new EntityNotFoundException("Charging station", "id", String.valueOf(chargingStationId)));
    }

    @Override
    public ChargingStation getChargingStationByZipcode(int zipcode) {
        return chargingStationRepository.getChargingStationByZipcode(zipcode)
                .orElseThrow(() -> new EntityNotFoundException("Charging station", "zipcode", String.valueOf(zipcode)));
    }

    @Override
    public ChargingStation getChargingStationByGeolocation(double latitude, double longitude) {
        return chargingStationRepository.getChargingStationByGeolocation(latitude, longitude)
                .orElseThrow(() -> new EntityNotFoundException("Charging station", "geolocation", String.valueOf(latitude + " " + longitude)));
    }

    @Override
    public void addChargingStation(ChargingStation chargingStation) {
        Optional<ChargingStation> existingChargingStation = chargingStationRepository.getChargingStationByZipcode(chargingStation.getZipcode().getId());
        if (existingChargingStation.isPresent() && chargingStation.getLatitude() == existingChargingStation.get().getLatitude() && chargingStation.getLongitude() == existingChargingStation.get().getLatitude()) {
            throw new IllegalArgumentException("Charging station already exists");

        } else {
            chargingStationRepository.addChargingStation(chargingStation);

        }
    }

    @Override
    public void updateChargingStation(ChargingStation chargingStation) {
        getChargingStationById(chargingStation.getId());
        chargingStationRepository.updateChargingStation(chargingStation);

    }

    @Override
    public void deleteChargingStation(int chargingStationId) {
        chargingStationRepository.deleteChargingStation(chargingStationId);

    }
}

