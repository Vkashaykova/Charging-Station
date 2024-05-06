package com.example.hubject.helpers;

import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.dto.ChargingStationDto;
import org.springframework.stereotype.Component;

@Component
public class ChargingStationMapper {
    public ChargingStation fromDto(int id, ChargingStationDto dto) {
        ChargingStation chargingStation = fromDto(dto);
        chargingStation.setId(id);

        return chargingStation;
    }

    public ChargingStation fromDto(ChargingStationDto dto) {
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setLatitude(dto.getLatitude());
        chargingStation.setLongitude(dto.getLongitude());
        chargingStation.setZipcode(dto.getZipcode());

        return chargingStation;
    }
}
