package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListPersonAddressDTO {
    private List<PersonAddressDTO> personAddressDTOs;

    public ListPersonAddressDTO() {
    }

    public ListPersonAddressDTO(List<PersonAddressDTO> personAddressDTOs) {
        this.personAddressDTOs = personAddressDTOs;
    }

    public List<PersonAddressDTO> getPersonAddressDTO() {
        return personAddressDTOs;
    }

    public void setPersonAddressDTO(List<PersonAddressDTO> personAddressDTOs) {
        this.personAddressDTOs = personAddressDTOs;
    }

    @Override
    public String toString() {
        return "ListPersonAddressDTO{" +
                "personAddressDTOList=" + personAddressDTOs +
                '}';
    }
}
