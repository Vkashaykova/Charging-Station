package com.example.hubject.services;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import com.example.hubject.repositories.contracts.ZipcodeRepository;
import com.example.hubject.services.contracts.ZipcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer implementation for managing zip codes.
 * This class provides methods to interact with the repository layer
 * and perform CRUD operations on zip codes.
 */
@Service
public class ZipcodeServiceImpl implements ZipcodeService {

    /**
     * Repository for accessing zip code data.
     */
    private final ZipcodeRepository zipcodeRepository;

    /**
     * Repository for accessing charging station data.
     */
    private final ChargingStationRepository chargingStationRepository;

    /**
     * Constructor to inject ZipcodeRepository and ChargingStationRepository dependencies.
     *
     * @param zipcodeRepository         Repository for accessing zip code data.
     * @param chargingStationRepository Repository for accessing charging station data.
     */

    @Autowired
    public ZipcodeServiceImpl(ZipcodeRepository zipcodeRepository,
                              ChargingStationRepository chargingStationRepository) {
        this.zipcodeRepository = zipcodeRepository;
        this.chargingStationRepository = chargingStationRepository;
    }

    /**
     * Retrieves a zip code by its ID.
     *
     * @param zipcodeId The ID of the zip code to retrieve.
     * @return The zip code with the specified ID.
     * @throws EntityNotFoundException If the zip code with the specified ID is not found.
     */
    @Override
    public Zipcode getZipcodeById(int zipcodeId) {
        return zipcodeRepository.getZipcodeById(zipcodeId)
                .orElseThrow(() -> new EntityNotFoundException("Zipcode", "zipcode", String.valueOf(zipcodeId)));
    }

    /**
     * Retrieves a zip code by its value.
     *
     * @param zipcode The value of the zip code to retrieve.
     * @return An optional containing the zip code with the specified value, if found.
     */
    @Override
    public Optional<Zipcode> getZipcodeByValue(int zipcode) {
        return zipcodeRepository.getZipcodeByValue(zipcode);
    }

    /**
     * Adds a new zip code.
     *
     * @param zipcode The zip code to add.
     */
    @Override
    public void addZipcode(Zipcode zipcode) {
        zipcodeRepository.addZipcode(zipcode);
    }

    /**
     * Updates an existing zip code.
     *
     * @param zipcode The zip code to update.
     * @throws DuplicateEntityException If a zip code with the same value already exists.
     */
    @Override
    public void updateZipcode(Zipcode zipcode) {
        Optional<Zipcode> existingZipcode = zipcodeRepository.getZipcodeByValue(zipcode.getZipcode());
        if (existingZipcode.isPresent()) {
            throw new DuplicateEntityException("Zipcode");
        }
        zipcodeRepository.updateZipcode(zipcode);
    }

    /**
     * Deletes a zip code.
     * Removes the zip code association from all associated charging stations
     * and deletes the zip code from the repository.
     *
     * @param zipcode The zip code to delete.
     */

    @Override
    public void deleteZipCode(Zipcode zipcode) {
        Optional<List<ChargingStation>> chargingStations = chargingStationRepository
                .getChargingStationByZipcode(zipcode.getZipcode());
        for (ChargingStation chargingStation : chargingStations.get()) {
            chargingStation.setZipcode(null);
            chargingStationRepository.updateChargingStation(chargingStation);
        }

        zipcodeRepository.deleteZipCode(zipcode);
    }
}
