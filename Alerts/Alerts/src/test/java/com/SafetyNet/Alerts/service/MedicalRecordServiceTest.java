package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.repository.MedicalRecordRepository;
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


public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<MedicalRecord> getMedicalRecords() {

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Dee");
        medicalRecord1.setBirthdate("01/08/1990");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord1.setFirstName("Steve");
        medicalRecord1.setLastName("Jobs");
        medicalRecord1.setBirthdate("08/01/2000");

        List<MedicalRecord> medicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);

        return medicalRecords;
    }

    @Test
    public void testGetMedicalRecords() {

        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        List<MedicalRecord> result = medicalRecordService.getMedicalRecords();
        assertEquals(2, result.size());
        assertEquals(medicalRecords, result);
    }

    @Test
    public void testSave() {
        MedicalRecord medicalRecord = getMedicalRecords().get(0);

        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);

        MedicalRecord result = medicalRecordService.save(medicalRecord);
        assertEquals(medicalRecord, result);
    }

    @Test
    public void testGetMedicalRecordByStation() {
        MedicalRecord medicalRecord = getMedicalRecords().get(1);

        when(medicalRecordRepository.findByFirstNameAndLastName("Steve", "Jobs")).thenReturn(medicalRecord);

        MedicalRecord result = medicalRecordService.getMedicalRecordByKey("Steve", "Jobs");
        assertEquals(medicalRecord, result);
    }

    @Test
    public void testDeleteMedicalRecord() {
        MedicalRecord medicalRecord = getMedicalRecords().get(1);

        when(medicalRecordRepository.findByFirstNameAndLastName("Steve", "Jobs")).thenReturn(medicalRecord);
        doNothing().when(medicalRecordRepository).delete(medicalRecord);

        medicalRecordService.deleteMedicalRecord("Steve", "Jobs");

        verify(medicalRecordRepository, times(1)).delete(medicalRecord);
    }

    @Test
    public void testDeleteMedicalRecordNotFound() {
        when(medicalRecordRepository.findByFirstNameAndLastName("Steve", "Jobs")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medicalRecordService.deleteMedicalRecord("Steve", "Jobs");
        });

        assertEquals("Medical record not found", exception.getMessage());
    }

    @Test
    public void testUpdateMedicalrecord() {
        MedicalRecord existingMedicalRecord = getMedicalRecords().get(0);
        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setFirstName("John");
        updatedMedicalRecord.setLastName("Doe");
        updatedMedicalRecord.setBirthdate("01/08/1990");

        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(existingMedicalRecord);
        when(medicalRecordRepository.save(existingMedicalRecord)).thenReturn(existingMedicalRecord);

        MedicalRecord result = medicalRecordService.updateMedicalrecord(updatedMedicalRecord);
        assertEquals(updatedMedicalRecord.getBirthdate(), result.getBirthdate());
    }
}
