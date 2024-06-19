package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListPersonDTO {
    private List<PersonDTO> personDTOs;

    public ListPersonDTO() {
    }

    public ListPersonDTO(List<PersonDTO> personDTOs) {
        this.personDTOs = personDTOs;
    }

    public List<PersonDTO> getPersonDTO() {
        return personDTOs;
    }

    public void setPersonDTO(List<PersonDTO> personDTOs) {
        this.personDTOs = personDTOs;
    }

    @Override
    public String toString() {
        return "ListPersonDTO{" +
                "personDTOs=" + personDTOs +
                '}';
    }
}
