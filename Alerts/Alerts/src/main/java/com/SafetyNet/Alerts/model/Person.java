package com.SafetyNet.Alerts.model;

public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;

    public String getFirstName() { return firstName; }
    public void setFirstName() { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName() { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress() { this.address = address; }
    public String getCity() { return city; }
    public void setCity() { this.city = city; }
    public int getZip() { return zip; }
    public void setZip(int zip) { this.zip = zip; }
    public String getPhone() { return phone; }
    public void setPhone() { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail() { this.email = email; }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
