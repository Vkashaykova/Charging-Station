package com.example.hubject.controllers.rest;

import com.example.hubject.helpers.ChargingStationMapper;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.dto.ChargingStationDto;
import com.example.hubject.services.contracts.ChargingStationService;
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
    private  final ChargingStationService chargingStationService;
    private final ChargingStationMapper chargingStationMapper;
@Autowired
    public ChargingStationRestController(ChargingStationService chargingStationService,
                                         ChargingStationMapper chargingStationMapper) {
        this.chargingStationService = chargingStationService;
    this.chargingStationMapper = chargingStationMapper;
}

    @GetMapping
    public  ResponseEntity<List<ChargingStation>> getAllChargingStations() {
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
    public ResponseEntity<ChargingStation> getChargingStationsByZipcode(@RequestParam String zipcode) {
        try {
            ChargingStation chargingStation = chargingStationService.getChargingStationByZipcode(Integer.parseInt(zipcode));
            return new ResponseEntity<>(chargingStation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping(value = "/search", params = "geolocation")
    public ResponseEntity<ChargingStation> getChargingStationsByGeolocation(@RequestParam double latitude,
                                                                            @RequestParam double longitude) {
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
                ChargingStation newChargingStation =  chargingStationMapper.fromDto(chargingStationDto);
                chargingStationService.addChargingStation(newChargingStation);
                return new ResponseEntity<>(newChargingStation, HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

}
