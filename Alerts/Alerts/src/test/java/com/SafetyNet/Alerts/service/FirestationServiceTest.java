package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.repository.FirestationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FirestationServiceTest {

    @Mock
    private FirestationRepository firestationRepository;

    @InjectMocks
    private FirestationService firestationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Firestation> getFirestations() {

        Firestation firestation1 = new Firestation();
        firestation1.setStation("1");
        firestation1.setAddress("1509 Culver St");

        Firestation firestation2 = new Firestation();
        firestation2.setStation("2");
        firestation2.setAddress("29 15th St");

        return Arrays.asList(firestation1, firestation2);
    }

    @Test
    public void testGetFirestations() {

        List<Firestation> firestations = getFirestations();

        when(firestationRepository.getAll()).thenReturn(firestations);

        List<Firestation> result = firestationService.getFirestations();
        assertEquals(2, result.size());
        assertEquals(firestations, result);
    }

    @Test
    public void testSave() {
        Firestation firestation = getFirestations().get(0);

        when(firestationRepository.save(firestation)).thenReturn(firestation);

        Firestation result = firestationService.save(firestation);
        assertEquals(firestation, result);
    }

    @Test
    public void testGetFirestationByStation() {
        Firestation firestation = getFirestations().get(0);

        when(firestationRepository.findByKey("1")).thenReturn(firestation);

        Firestation result = firestationService.getFirestationByStation("1");
        assertEquals(firestation, result);
    }

    @Test
    public void testDeleteFirestation() {
        Firestation firestation = getFirestations().get(0);

        when(firestationRepository.findByKey("1")).thenReturn(firestation);
        doNothing().when(firestationRepository).delete(firestation);

        firestationService.deleteFirestation("1");

        verify(firestationRepository, times(1)).delete(firestation);
    }

    @Test
    public void testDeleteFirestationNotFound() {
        when(firestationRepository.findByKey("1")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            firestationService.deleteFirestation("1");
        });

        assertEquals("Firestation not found", exception.getMessage());
    }

    @Test
    public void testUpdateFirestation() {
        Firestation existingFirestation = getFirestations().get(0);
        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setStation("3");
        updatedFirestation.setAddress("1509 Culver St");

        when(firestationRepository.findByKey("3")).thenReturn(existingFirestation);
        when(firestationRepository.save(existingFirestation)).thenReturn(existingFirestation);

        Firestation result = firestationService.updateFirestation(updatedFirestation);
        assertEquals(updatedFirestation.getStation(), result.getStation());
    }
}
