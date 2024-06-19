package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListPhoneNumberStationDTO {

    private List<PhoneNumberStationDTO> phoneNumberStationDTOs;

    public ListPhoneNumberStationDTO() {
    }

    public ListPhoneNumberStationDTO(List<PhoneNumberStationDTO> phoneNumberStationDTOs) {
        this.phoneNumberStationDTOs = phoneNumberStationDTOs;
    }

    public List<PhoneNumberStationDTO> getPhoneNumberStationDTOs() {
        return phoneNumberStationDTOs;
    }

    public void setPhoneNumberStationDTOs(List<PhoneNumberStationDTO> phoneNumberStationDTOs) {
        this.phoneNumberStationDTOs = phoneNumberStationDTOs;
    }

    @Override
    public String toString() {
        return "ListPhoneNumberStationDTO{" +
                "phoneNumberStationDTOs=" + phoneNumberStationDTOs +
                '}';
    }
}
