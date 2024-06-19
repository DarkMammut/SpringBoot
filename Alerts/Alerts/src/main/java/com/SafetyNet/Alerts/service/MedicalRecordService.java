package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getMedicalRecords() {
        logger.info("Fetching all medical records");
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        logger.info("Number of medical records fetched: {}", medicalRecords.size());
        return medicalRecords;
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        logger.info("Saving medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        logger.info("Medical record saved for: {} {}", savedMedicalRecord.getFirstName(), savedMedicalRecord.getLastName());
        return savedMedicalRecord;
    }

    public MedicalRecord getMedicalRecordByKey(String firstName, String lastName) {
        logger.info("Fetching medical record for: {} {}", firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord == null) {
            logger.warn("Medical record not found for: {} {}", firstName, lastName);
            throw new RuntimeException("Medical record not found");
        }
        return medicalRecord;
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Deleting medical record for: {} {}", firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord == null) {
            logger.error("Medical record not found for: {} {}", firstName, lastName);
            throw new RuntimeException("Medical record not found");
        }
        medicalRecordRepository.delete(medicalRecord);
        logger.info("Medical record deleted for: {} {}", firstName, lastName);
    }

    public MedicalRecord updateMedicalrecord(MedicalRecord updatedMedicalRecord) {
        logger.info("Updating medical record with name: {} {}", updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
        MedicalRecord existingMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
        if (existingMedicalRecord == null) {
            logger.error("Medicalb record to update not found with name: {} {}", updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
            throw new RuntimeException("MedicalRecord to update not found");
        }

        existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
        existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
        existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());

        MedicalRecord updated = medicalRecordRepository.save(existingMedicalRecord);
        logger.info("Medical record updated with name: {} {}", updated.getFirstName(), updated.getLastName());
        return updated;
    }
}
