package com.example.hubject.services;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import com.example.hubject.services.contracts.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer implementation for managing charging stations.
 * This class provides methods to interact with the repository layer
 * and perform CRUD operations on charging stations.
 */
@Service
public class ChargingStationServiceImpl implements ChargingStationService {

    /**
     * Repository for accessing charging station data.
     */
    private final ChargingStationRepository chargingStationRepository;

    /**
     * Constructor to inject ChargingStationRepository dependency.
     *
     * @param chargingStationRepository Repository for accessing charging station data.
     */
    @Autowired
    public ChargingStationServiceImpl(ChargingStationRepository chargingStationRepository) {
        this.chargingStationRepository = chargingStationRepository;
    }

    /**
     * Retrieves all charging stations.
     *
     * @return A list of all charging stations.
     */
    @Override
    public List<ChargingStation> getAllChargingStations() {

        return chargingStationRepository.getAllChargingStations();
    }

    /**
     * Retrieves a charging station by its ID.
     *
     * @param chargingStationId The ID of the charging station to retrieve.
     * @return The charging station with the specified ID.
     * @throws EntityNotFoundException If the charging station with the specified ID is not found.
     */
    @Override
    public ChargingStation getChargingStationById(int chargingStationId) {
        return chargingStationRepository.getChargingStationById(chargingStationId)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Charging station", "id", String.valueOf(chargingStationId)));
    }

    /**
     * Retrieves charging stations by zipcode.
     *
     * @param zipcode The zipcode to filter charging stations.
     * @return A list of charging stations with the specified zipcode.
     * @throws EntityNotFoundException If no charging stations are found with the specified zipcode.
     */
    @Override
    public List<ChargingStation> getChargingStationByZipcode(int zipcode) {
        return chargingStationRepository.getChargingStationByZipcode(zipcode)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Charging station", "zipcode", String.valueOf(zipcode)));
    }

    /**
     * Retrieves a charging station by its geolocation coordinates.
     *
     * @param latitude  The latitude of the charging station.
     * @param longitude The longitude of the charging station.
     * @return The charging station at the specified geolocation.
     * @throws EntityNotFoundException If no charging station is found at the specified geolocation.
     */
    @Override
    public ChargingStation getChargingStationByGeolocation(double latitude, double longitude) {
        return chargingStationRepository.getChargingStationByGeolocation(latitude, longitude)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Charging station", "geolocation", (latitude + " " + longitude)));
    }

    /**
     * Adds a new charging station.
     *
     * @param chargingStation The charging station to add.
     * @throws DuplicateEntityException If a charging station already exists at the specified geolocation and zipcode.
     */
    @Override
    public void addChargingStation(ChargingStation chargingStation) {
        Optional<ChargingStation> existingChargingStation = chargingStationRepository.
                getChargingStationByGeolocation(chargingStation.getLatitude(), chargingStation.getLongitude());

        if (existingChargingStation.isPresent() && existingChargingStation.get().getZipcode() != null
                && existingChargingStation.get().getZipcode().getZipcode() == chargingStation.getZipcode().getZipcode()
        ) {
            throw new DuplicateEntityException("Charging station");
        } else {
            chargingStationRepository.addChargingStation(chargingStation);
        }
    }

    /**
     * Updates an existing charging station.
     *
     * @param chargingStation The charging station to update.
     * @throws EntityNotFoundException  If the charging station to update is not found.
     * @throws DuplicateEntityException If a charging station already exists at the specified geolocation and zipcode.
     */

    @Override
    public void updateChargingStation(ChargingStation chargingStation) {
        getChargingStationById(chargingStation.getId());
        Optional<ChargingStation> existingChargingStation = chargingStationRepository.getChargingStationByGeolocation
                (chargingStation.getLatitude(), chargingStation.getLongitude());

        if (existingChargingStation.isPresent() &&
                existingChargingStation.get().getZipcode().getZipcode() == chargingStation.getZipcode().getZipcode()) {
            throw new DuplicateEntityException("Charging station", "geolocation", "and zipcode");
        }
        chargingStationRepository.updateChargingStation(chargingStation);
    }

    /**
     * Deletes a charging station.
     *
     * @param chargingStation The charging station to delete.
     */

    @Override
    public void deleteChargingStation(ChargingStation chargingStation) {
        chargingStationRepository.deleteChargingStation(chargingStation);
    }
}

