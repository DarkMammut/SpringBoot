package com.SafetyNet.Alerts.service.dto;

public class EmailCityDTO {
    private String email;

    public EmailCityDTO() {
    }

    public EmailCityDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailCityDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
