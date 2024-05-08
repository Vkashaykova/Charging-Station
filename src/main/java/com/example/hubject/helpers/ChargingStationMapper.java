package com.example.hubject.helpers;

import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;
import com.example.hubject.models.dto.ChargingStationDto;
import com.example.hubject.models.dto.ZipcodeDto;
import com.example.hubject.services.contracts.ZipcodeService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChargingStationMapper {

    private final ZipcodeService zipcodeService;

    public ChargingStationMapper(ZipcodeService zipcodeService) {
        this.zipcodeService = zipcodeService;
    }

    public ChargingStation fromDto(int id, ChargingStationDto dto) {
        ChargingStation chargingStation = fromDto(dto);
        chargingStation.setId(id);

        return chargingStation;
    }

    public ChargingStation fromDto(ChargingStationDto dto) {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setLatitude(dto.getLatitude());
        chargingStation.setLongitude(dto.getLongitude());

        Optional<Zipcode>existingZipcode = zipcodeService.getZipcodeByValue(dto.getZipcode());

        if (existingZipcode.isPresent()) {
            chargingStation.setZipcode(existingZipcode.get());
        } else {
            Zipcode newZipcode = new Zipcode();
            newZipcode.setZipcode(dto.getZipcode());
            zipcodeService.addZipcode(newZipcode);
            chargingStation.setZipcode(newZipcode);
        }

        return chargingStation;
    }

    public Zipcode zipcodeFromDto(int id, ZipcodeDto dto) {
        Zipcode zipcode = zipcodeFromDto(dto);
        zipcode.setId(id);

        return zipcode;
    }
    public Zipcode zipcodeFromDto(ZipcodeDto dto) {
        Zipcode zipcode = new Zipcode();
        zipcode.setZipcode(dto.getZipcode());

        return zipcode;
    }
}
