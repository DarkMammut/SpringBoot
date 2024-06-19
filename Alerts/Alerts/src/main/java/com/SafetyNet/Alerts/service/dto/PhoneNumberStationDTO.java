package com.SafetyNet.Alerts.service.dto;

public class PhoneNumberStationDTO {

    private String phoneNumber;

    public PhoneNumberStationDTO() {
    }

    public PhoneNumberStationDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneNumberStationDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
