package com.SafetyNet.Alerts.model;

import java.util.List;

public class FirestationsList {
    private List<Firestation> firestations;

    public FirestationsList() {
    }

    public FirestationsList(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }

    @Override
    public String toString() {
        return "FirestationsList{" +
                "firestations=" + firestations +
                '}';
    }
}
