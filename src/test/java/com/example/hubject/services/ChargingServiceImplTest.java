package com.example.hubject.services;

import com.example.hubject.exceptions.DuplicateEntityException;
import com.example.hubject.exceptions.EntityNotFoundException;
import com.example.hubject.models.ChargingStation;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.hubject.helpers.createMockChargingStation;
import static org.junit.jupiter.api.Assertions.*;

public class ChargingServiceImplTest {
    @Mock
    private ChargingStationRepository mockRepository;

    @InjectMocks
    private ChargingStationServiceImpl chargingStationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllChargingStations_Should_ReturnListOfChargingStations() {
        // Arrange
        ChargingStation chargingStation = createMockChargingStation();
        List<ChargingStation> expectedChargingStations = Collections.singletonList(chargingStation);
        Mockito.when(mockRepository.getAllChargingStations()).thenReturn(expectedChargingStations);

        // Act
        List<ChargingStation> actualChargingStations = chargingStationService.getAllChargingStations();

        // Assert
        assertArrayEquals(expectedChargingStations.toArray(), actualChargingStations.toArray());
    }

    @Test
    public void getChargingStationById_WithValidId_ShouldReturnChargingStation() {
        // Arrange
        int validId = 1;
        ChargingStation expectedChargingStation = createMockChargingStation();
        Mockito.when(mockRepository.getChargingStationById(validId)).thenReturn(Optional.of(expectedChargingStation));

        // Act
        ChargingStation actualChargingStation = chargingStationService.getChargingStationById(validId);

        // Assert
        assertEquals(expectedChargingStation, actualChargingStation);
    }

    @Test
    public void getChargingStationById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        int invalidId = 999;
        Mockito.when(mockRepository.getChargingStationById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chargingStationService.getChargingStationById(invalidId));
    }

    @Test
    public void getChargingStationByZipcode_WithValidZipcode_ShouldReturnListOfChargingStations() {
        // Arrange
        int validZipcode = 12345;

        List<ChargingStation> expectedChargingStations = new ArrayList<>();
        ChargingStation expectedChargingStation = createMockChargingStation();
        expectedChargingStations.add(expectedChargingStation);

        Mockito.when(mockRepository.getChargingStationByZipcode(validZipcode))
                .thenReturn(Optional.of(expectedChargingStations));

        // Act
        List<ChargingStation> actualChargingStations = chargingStationService.getChargingStationByZipcode(validZipcode);

        // Assert
        assertEquals(expectedChargingStations, actualChargingStations);
    }

    @Test
    public void getChargingStationByZipcode_WithInvalidZipcode_ShouldThrowEntityNotFoundException() {
        // Arrange
        int invalidZipcode = 99999;
        Mockito.when(mockRepository.getChargingStationByZipcode(invalidZipcode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chargingStationService
                .getChargingStationByZipcode(invalidZipcode));
    }

    @Test
    public void getChargingStationByGeolocation_WithValidCoordinates_ShouldReturnChargingStation() {
        // Arrange
        double validLatitude = 37.7749;
        double validLongitude = -122.4194;
        ChargingStation expectedChargingStation = createMockChargingStation();
        Mockito.when(mockRepository.getChargingStationByGeolocation(validLatitude, validLongitude))
                        .thenReturn(Optional.of(expectedChargingStation));

        // Act
        ChargingStation actualChargingStation = chargingStationService
                .getChargingStationByGeolocation(validLatitude, validLongitude);

        // Assert
        assertEquals(expectedChargingStation, actualChargingStation);
    }

    @Test
    public void getChargingStationByGeolocation_WithInvalidCoordinates_ShouldThrowEntityNotFoundException() {
        // Arrange
        double invalidLatitude = 1000.0;
        double invalidLongitude = 2000.0;
        Mockito.when(mockRepository.getChargingStationByGeolocation(invalidLatitude, invalidLongitude))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> chargingStationService
                .getChargingStationByGeolocation(invalidLatitude, invalidLongitude));
    }

    @Test
    public void addChargingStation_WhenNoExistingChargingStation_ShouldAddChargingStation() {
        // Arrange
        ChargingStation newChargingStation = createMockChargingStation();
        Mockito.when(mockRepository.getChargingStationByGeolocation(newChargingStation.getLatitude(),
                newChargingStation.getLongitude())).thenReturn(Optional.empty());

        // Act
        chargingStationService.addChargingStation(newChargingStation);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1)).addChargingStation(newChargingStation);
    }

    @Test
    public void addChargingStation_WhenExistingChargingStationWithSameGeolocationAndZipcode_ShouldThrowDuplicateEntityException() {
        // Arrange
        ChargingStation existingChargingStation = createMockChargingStation();
        ChargingStation newChargingStation = createMockChargingStation();

        newChargingStation.setLatitude(existingChargingStation.getLatitude());
        newChargingStation.setLongitude(existingChargingStation.getLongitude());
        newChargingStation.setZipcode(existingChargingStation.getZipcode());

        Mockito.when(mockRepository.getChargingStationByGeolocation(existingChargingStation.getLatitude(),
                existingChargingStation.getLongitude())).thenReturn(Optional.of(existingChargingStation));

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> chargingStationService.addChargingStation(newChargingStation));
    }

    @Test
    public void updateChargingStation_WhenNoConflict_ShouldUpdateChargingStation() {
        // Arrange
        ChargingStation chargingStationToUpdate = createMockChargingStation();
        Mockito.when(mockRepository.getChargingStationById(chargingStationToUpdate.getId()))
                .thenReturn(Optional.of(chargingStationToUpdate));
        Mockito.when(mockRepository.getChargingStationByGeolocation(chargingStationToUpdate.getLatitude(),
                chargingStationToUpdate.getLongitude())).thenReturn(Optional.empty());

        // Act
        chargingStationService.updateChargingStation(chargingStationToUpdate);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1)).
                updateChargingStation(chargingStationToUpdate);
    }

    @Test
    public void updateChargingStation_WhenConflictWithExistingChargingStation_ShouldThrowDuplicateEntityException() {
        // Arrange
        ChargingStation chargingStationToUpdate = createMockChargingStation();
        ChargingStation existingChargingStation = createMockChargingStation();

        chargingStationToUpdate.setLatitude(existingChargingStation.getLatitude());
        chargingStationToUpdate.setLongitude(existingChargingStation.getLongitude());
        chargingStationToUpdate.setZipcode(existingChargingStation.getZipcode());

        Mockito.when(mockRepository.getChargingStationById(chargingStationToUpdate.getId()))
                        .thenReturn(Optional.of(chargingStationToUpdate));
        Mockito.when(mockRepository.getChargingStationByGeolocation(chargingStationToUpdate
                .getLatitude(), chargingStationToUpdate.getLongitude())).thenReturn(Optional.of(existingChargingStation));

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> chargingStationService
                .updateChargingStation(chargingStationToUpdate));
    }

    @Test
    public void deleteChargingStation_ShouldCallRepositoryDeleteMethodWithCorrectChargingStation() {
        // Arrange
        ChargingStation chargingStationToDelete = createMockChargingStation();

        // Act
        chargingStationService.deleteChargingStation(chargingStationToDelete);

        // Assert
        Mockito.verify(mockRepository, Mockito.times(1)).
                deleteChargingStation(chargingStationToDelete);
    }
}
