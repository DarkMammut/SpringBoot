package com.SafetyNet.Alerts.model;

import java.util.List;

public class MedicalRecordsList {
    private List<MedicalRecords> medicalrecords;

    public MedicalRecordsList() {
    }

    public MedicalRecordsList(List<MedicalRecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @Override
    public String toString() {
        return "MedicalRecordsList{" +
                "medicalrecords=" + medicalrecords +
                '}';
    }
}
