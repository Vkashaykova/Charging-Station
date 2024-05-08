package com.example.hubject.services;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.models.Zipcode;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import com.example.hubject.repositories.contracts.ZipcodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.hubject.helpers.createMockZipcode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ZipcodeServiceImplTest {
    @Mock
    private ZipcodeRepository mockRepository;
    private ChargingStationRepository chargingStationRepository;

    @InjectMocks
    private ZipcodeServiceImpl zipcodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getZipcodeById_WithValidZipcodeId_ShouldReturnZipcode() {
        // Arrange
        int validZipcodeId = 12345;
        Zipcode expectedZipcode = createMockZipcode();
        when(mockRepository.getZipcodeById(validZipcodeId)).thenReturn(Optional.of(expectedZipcode));

        // Act
        Zipcode actualZipcode = zipcodeService.getZipcodeById(validZipcodeId);

        // Assert
        assertEquals(expectedZipcode, actualZipcode);
    }

    @Test
    public void getZipcodeById_WithInvalidZipcodeId_ShouldThrowEntityNotFoundException() {
        // Arrange
        int invalidZipcodeId = 99999;
        when(mockRepository.getZipcodeById(invalidZipcodeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            zipcodeService.getZipcodeById(invalidZipcodeId);
        });
    }

    @Test
    public void getZipcodeByValue_WithValidZipcode_ShouldReturnOptionalContainingZipcode() {
        // Arrange
        int validZipcodeValue = 12345;
        Zipcode expectedZipcode = createMockZipcode();
        when(mockRepository.getZipcodeByValue(validZipcodeValue)).thenReturn(Optional.of(expectedZipcode));

        // Act
        Optional<Zipcode> actualZipcodeOptional = zipcodeService.getZipcodeByValue(validZipcodeValue);

        // Assert
        assertTrue(actualZipcodeOptional.isPresent());
        assertEquals(expectedZipcode, actualZipcodeOptional.get());
    }

    @Test
    public void getZipcodeByValue_WithInvalidZipcode_ShouldReturnEmptyOptional() {
        // Arrange
        int invalidZipcodeValue = 99999;
        when(mockRepository.getZipcodeByValue(invalidZipcodeValue)).thenReturn(Optional.empty());

        // Act
        Optional<Zipcode> actualZipcodeOptional = zipcodeService.getZipcodeByValue(invalidZipcodeValue);

        // Assert
        assertFalse(actualZipcodeOptional.isPresent());
    }

    @Test
    public void addZipcode_ShouldCallRepositoryAddMethodWithCorrectZipcode() {
        // Arrange
        Zipcode zipcodeToAdd = createMockZipcode();

        // Act
        zipcodeService.addZipcode(zipcodeToAdd);

        // Assert
        verify(mockRepository, times(1)).addZipcode(zipcodeToAdd);
    }

    @Test
    public void updateZipcode_WhenNoDuplicate_ShouldUpdateZipcode() {
        // Arrange
        Zipcode zipcodeToUpdate = createMockZipcode();
        when(mockRepository.getZipcodeByValue(zipcodeToUpdate.getZipcode())).thenReturn(Optional.empty());

        // Act
        zipcodeService.updateZipcode(zipcodeToUpdate);

        // Assert
        verify(mockRepository, times(1)).updateZipcode(zipcodeToUpdate);
    }

    @Test
    public void updateZipcode_WhenDuplicateFound_ShouldThrowDuplicateEntityException() {
        // Arrange
        Zipcode zipcodeToUpdate = createMockZipcode();
        when(mockRepository.getZipcodeByValue(zipcodeToUpdate.getZipcode())).
                thenReturn(Optional.of(zipcodeToUpdate));

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> {
            zipcodeService.updateZipcode(zipcodeToUpdate);
        });
    }

    @Test
    public void deleteZipCode_ShouldRemoveZipcodeFromAssociatedChargingStationsAndDeleteZipcode() {
        // Arrange
        Zipcode zipcode = createMockZipcode();
        ChargingStation chargingStation1 = new ChargingStation();
        ChargingStation chargingStation2 = new ChargingStation();
        List<ChargingStation> chargingStations = Arrays.asList(chargingStation1, chargingStation2);
        when(chargingStationRepository.getChargingStationByZipcode(zipcode.getZipcode())).thenReturn(Optional.of(chargingStations));

        // Act
        zipcodeService.deleteZipCode(zipcode);

        // Assert
        for (ChargingStation chargingStation : chargingStations) {
            assertNull(chargingStation.getZipcode());
            verify(chargingStationRepository, times(1)).updateChargingStation(chargingStation); // Verify updateChargingStation called
        }
        verify(mockRepository, times(1)).deleteZipCode(zipcode);
    }
}
