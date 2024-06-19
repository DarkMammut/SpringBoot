package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListPersonMedicalRecordDTO {

    private List<PersonMedicalRecordDTO> personMedicalRecordDTOs;
    private int adult;
    private int children;

    public ListPersonMedicalRecordDTO() {
    }

    public ListPersonMedicalRecordDTO(List<PersonMedicalRecordDTO> personMedicalRecordDTOs, int adult, int children) {
        this.personMedicalRecordDTOs = personMedicalRecordDTOs;
        this.adult = adult;
        this.children = children;
    }

    public List<PersonMedicalRecordDTO> getPersonMedicalRecordDTOs() {
        return personMedicalRecordDTOs;
    }

    public void setPersonMedicalRecordDTOs(List<PersonMedicalRecordDTO> personMedicalRecordDTOs) {
        this.personMedicalRecordDTOs = personMedicalRecordDTOs;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ListPersonMedicalRecordDTO{" +
                "personMedicalRecordDTOs=" + personMedicalRecordDTOs +
                ", adult=" + adult +
                ", children=" + children +
                '}';
    }
}
