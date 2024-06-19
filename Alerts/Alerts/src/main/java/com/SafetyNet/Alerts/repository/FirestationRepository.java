package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Firestation;

import java.util.List;

public interface FirestationRepository extends CrudRepository<Firestation> {
    List<Firestation> findByStationNumber(String stationNumber);
    List<Firestation> findByStationNumbers(List<String> stationNumbers);
}
