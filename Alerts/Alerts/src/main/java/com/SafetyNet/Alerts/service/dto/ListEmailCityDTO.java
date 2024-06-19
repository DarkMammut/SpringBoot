package com.SafetyNet.Alerts.service.dto;

import java.util.List;

public class ListEmailCityDTO {

    private List<EmailCityDTO> emailCityDTOs;

    public ListEmailCityDTO() {
    }

    public ListEmailCityDTO(List<EmailCityDTO> emailCityDTOs) {
        this.emailCityDTOs = emailCityDTOs;
    }

    public List<EmailCityDTO> getEmailCityDTO() {
        return emailCityDTOs;
    }

    public void setEmailCityDTO(List<EmailCityDTO> emailCityDTOs) {
        this.emailCityDTOs = emailCityDTOs;
    }

    @Override
    public String toString() {
        return "ListEmailCityDTO{" +
                "emailCityDTOs=" + emailCityDTOs +
                '}';
    }
}
