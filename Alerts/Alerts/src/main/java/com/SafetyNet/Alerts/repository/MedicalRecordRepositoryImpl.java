package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.util.JsonDataImporter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private MedicalRecordRepositoryImpl(JsonDataImporter jsonDataImporter) {
        this.medicalRecords = JsonDataImporter.medicalRecords();
    }

    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    @Override
    public List<MedicalRecord> getAll() {
        return medicalRecords;
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public MedicalRecord update(MedicalRecord updatedMedicalRecord) {

            MedicalRecord existingMedicalRecord = findByFirstNameAndLastName(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
            if (existingMedicalRecord == null) {
                throw new RuntimeException("MedicalRecord not found");
            }

            // Update the fields of the existing person with the new values
            existingMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
            existingMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
            existingMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());

            return existingMedicalRecord;
    }

    @Override
    public MedicalRecord findByKey(String key) {
        return null;
    }

    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        medicalRecords.remove(medicalRecord);
    }
}
