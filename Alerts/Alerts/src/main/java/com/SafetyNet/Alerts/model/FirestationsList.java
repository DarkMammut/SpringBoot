package com.SafetyNet.Alerts.model;

import java.util.List;

public class FirestationsList {
    private List<Firestations> firestations;

    public FirestationsList() {
    }

    public FirestationsList(List<Firestations> firestations) {
        this.firestations = firestations;
    }

    public List<Firestations> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestations> firestations) {
        this.firestations = firestations;
    }

    @Override
    public String toString() {
        return "FirestationsList{" +
                "firestations=" + firestations +
                '}';
    }
}
