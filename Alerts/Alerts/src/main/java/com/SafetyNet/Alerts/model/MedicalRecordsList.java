package com.SafetyNet.Alerts.model;

import java.util.List;

public class MedicalRecordsList {
    private List<MedicalRecord> medicalrecords;

    public MedicalRecordsList() {
    }

    public MedicalRecordsList(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @Override
    public String toString() {
        return "MedicalRecordsList{" +
                "medicalrecords=" + medicalrecords +
                '}';
    }
}
