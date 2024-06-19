package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.util.JsonDataImporter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FirestationRepositoryImpl implements FirestationRepository {

    private FirestationRepositoryImpl(JsonDataImporter jsonDataImporter) {
        this.firestations = JsonDataImporter.firestations();
    }

    private List<Firestation> firestations = new ArrayList<>();

    @Override
    public List<Firestation> getAll() {
        return firestations;
    }

    @Override
    public Firestation save(Firestation uptatedFirestation) {
        Firestation existingFirestation = findByKey(uptatedFirestation.getStation());
        if (existingFirestation != null) {
            throw new RuntimeException("Firestation not found");
        }

        // Update the fields of the existing firestation with the new values
        existingFirestation.setStation(uptatedFirestation.getStation());

        return existingFirestation;
    }

    @Override
    public Firestation update(Firestation firestation) {
        return null;
    }

    @Override
    public Firestation findByKey(String key) {
        return firestations.stream()
                .filter(person -> person.getStation().equals(key))
                .findFirst()          // Use findFirst() instead of toList()
                .orElse(null);        // Return null if no matching person is found
    }

    @Override
    public void delete(Firestation firestation) {
        firestations.remove(firestation);
    }

    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        return firestations.stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .collect(Collectors.toList());
    }

    @Override
    public List<Firestation> findByStationNumbers(List<String> stationNumbers) {
        return firestations.stream()
                .filter(firestation -> stationNumbers.contains(firestation.getStation()))
                .collect(Collectors.toList());
    }
}
