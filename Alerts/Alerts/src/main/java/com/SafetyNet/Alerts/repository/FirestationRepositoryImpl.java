package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.util.JsonDataImporter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationImpl implements FirestationRepository {
    private FirestationRepositoryImpl(JsonDataImporter jsonDataImporter) {
        this.firestations = jsonDataImporter.firestations();
    }

    private List<Firestation> firestations = new ArrayList<>();


}
