package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.MedicalRecord;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord> {
    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);
}
