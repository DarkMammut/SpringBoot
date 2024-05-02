package com.SafetyNet.Alerts.model;

import java.util.Date;

public class MedicalRecord {
    private String firstName;
    private String lastName;
    private Date birthdate;

    public String getFirstName() { return firstName; }
    public void setFirstName() { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName() { this.lastName = lastName; }
    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
}
