package com.example.hubject.controllers.rest;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.helpers.ChargingStationMapper;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;
import com.example.hubject.models.dto.ChargingStationDto;
import com.example.hubject.models.dto.ZipcodeDto;
import com.example.hubject.services.contracts.ChargingStationService;
import com.example.hubject.services.contracts.ZipcodeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/charging-stations")
public class ChargingStationRestController {
    private final ChargingStationService chargingStationService;
    private final ChargingStationMapper chargingStationMapper;
    private final ZipcodeService zipcodeService;

    @Autowired
    public ChargingStationRestController(ChargingStationService chargingStationService,
                                         ChargingStationMapper chargingStationMapper, ZipcodeService zipcodeService) {
        this.chargingStationService = chargingStationService;
        this.chargingStationMapper = chargingStationMapper;
        this.zipcodeService = zipcodeService;
    }

    @GetMapping
    public ResponseEntity<List<ChargingStation>> getAllChargingStations() {

        try {
            List<ChargingStation> chargingStations = chargingStationService.getAllChargingStations();
            return new ResponseEntity<>(chargingStations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{chargingStationId}")
    public ResponseEntity<ChargingStation> getChargingStationsById(@PathVariable int chargingStationId) {

        try {
            ChargingStation chargingStation = chargingStationService.getChargingStationById(chargingStationId);
            return new ResponseEntity<>(chargingStation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/search", params = "zipcode")
    public ResponseEntity<List<ChargingStation>> getChargingStationsByZipcode(@RequestParam String zipcode) {

        try {
            List<ChargingStation> chargingStations = chargingStationService.getChargingStationByZipcode(Integer.parseInt(zipcode));
            return new ResponseEntity<>(chargingStations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/search", params = {"latitude", "longitude"})
    public ResponseEntity<ChargingStation> getChargingStationsByGeolocation(@RequestParam("latitude") double latitude,
                                                                            @RequestParam("longitude") double longitude) {
        try {
            ChargingStation chargingStation = chargingStationService.getChargingStationByGeolocation(latitude, longitude);
            return new ResponseEntity<>(chargingStation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ChargingStation> addChargingStation(@RequestBody ChargingStationDto chargingStationDto) {

        try {
            ChargingStation newChargingStation = chargingStationMapper.fromDto(chargingStationDto);
            chargingStationService.addChargingStation(newChargingStation);
            return new ResponseEntity<>(newChargingStation, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{chargingStationId}")
    public ResponseEntity<ChargingStation> updateChargingStation(@PathVariable int chargingStationId,
                                                                 @RequestBody ChargingStationDto chargingStationDto) {
        try {
            chargingStationService.getChargingStationById(chargingStationId);
            ChargingStation updateChargingStation = chargingStationMapper.fromDto(chargingStationId, chargingStationDto);
            chargingStationService.updateChargingStation(updateChargingStation);
            return new ResponseEntity<>(updateChargingStation, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{chargingStationId}")
    public ResponseEntity<ChargingStation> deleteChargingStation(@PathVariable int chargingStationId) {

        try {
            ChargingStation delteChargingStation = chargingStationService.getChargingStationById(chargingStationId);
            chargingStationService.deleteChargingStation(delteChargingStation);
            return new ResponseEntity<>(delteChargingStation, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/zipcode/{zipcodeId}")
    public ResponseEntity<Zipcode> updateChargingStation(@PathVariable int zipcodeId,
                                                         @RequestBody ZipcodeDto zipCodeDto) {
        try {
            zipcodeService.getZipcodeById(zipcodeId);
            Zipcode updateZipcode = chargingStationMapper.zipcodeFromDto(zipcodeId, zipCodeDto);
            zipcodeService.updateZipcode(updateZipcode);
            return new ResponseEntity<>(updateZipcode, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/zipcode/{zipcodeId}")
    public ResponseEntity<Zipcode> deleteZipcode(@PathVariable int zipcodeId) {

        try {
            Zipcode deleteZipcode = zipcodeService.getZipcodeById(zipcodeId);
            zipcodeService.deleteZipCode(deleteZipcode);
            return new ResponseEntity<>(deleteZipcode, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
