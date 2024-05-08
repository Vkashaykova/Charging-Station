package com.example.hubject.services;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import com.example.hubject.repositories.contracts.ZipcodeRepository;
import com.example.hubject.services.contracts.ChargingStationService;
import com.example.hubject.services.contracts.ZipcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZipcodeServiceImpl implements ZipcodeService {
    private final ZipcodeRepository zipcodeRepository;
    private final ChargingStationRepository chargingStationRepository;

    @Autowired
    public ZipcodeServiceImpl(ZipcodeRepository zipcodeRepository, ChargingStationRepository chargingStationRepository) {
        this.zipcodeRepository = zipcodeRepository;
        this.chargingStationRepository = chargingStationRepository;
    }

    @Override
    public Zipcode getZipcodeById(int zipcodeId) {
        return zipcodeRepository.getZipcodeById(zipcodeId)
                .orElseThrow(() -> new EntityNotFoundException("Zipcode", "zipcode", String.valueOf(zipcodeId)));
    }
    @Override
    public Optional<Zipcode> getZipcodeByValue(int zipcode) {
        return zipcodeRepository.getZipcodeByValue(zipcode);
    }

    @Override
    public void addZipcode(Zipcode zipcode) {
        zipcodeRepository.addZipcode(zipcode);
    }

    @Override
    public void updateZipcode(Zipcode zipcode) {
       Optional<Zipcode>  existingZipcode = zipcodeRepository.getZipcodeByValue(zipcode.getZipcode());
        if(existingZipcode.isPresent()){
            throw new DuplicateEntityException("Zipcode");
        }
        zipcodeRepository.updateZipcode(zipcode);
    }

    @Override
    public void deleteZipCode(Zipcode zipcode) {
        Optional<List<ChargingStation>> chargingStations= chargingStationRepository.getChargingStationByZipcode(zipcode.getZipcode());
        for (ChargingStation chargingStation : chargingStations.get()) {
            chargingStation.setZipcode(null); // Remove Zipcode
            chargingStationRepository.updateChargingStation(chargingStation); // Update ChargingStation
        }

        zipcodeRepository.deleteZipCode(zipcode);
    }
}
