package com.SafetyNet.Alerts.model;

public class Firestation {

    private String address;
    private String station;

    public Firestation() {
    }

    ;

    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Firestations{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
