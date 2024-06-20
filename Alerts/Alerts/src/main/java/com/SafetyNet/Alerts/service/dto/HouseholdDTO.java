package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class HouseholdDTO {
    private String address;
    private List<PersonHouseholdDTO> persons;

    public HouseholdDTO() {}

    public HouseholdDTO(String address, List<PersonHouseholdDTO> persons) {
        this.address = address;
        this.persons = persons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonHouseholdDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonHouseholdDTO> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "HouseholdDTO{" +
                "address='" + address + '\'' +
                ", persons=" + persons +
                '}';
    }
}
