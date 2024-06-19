package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.repository.FirestationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    Logger logger = LoggerFactory.getLogger(FirestationService.class);

    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public List<Firestation> getFirestations() {
        logger.info("Fetching all firestations");
        List<Firestation> firestations = firestationRepository.getAll();
        logger.info("Number of firestations fetched: {}", firestations.size());
        return firestations;
    }

    public Firestation save(Firestation firestation) {
        logger.info("Saving firestation with station: {}", firestation.getStation());
        Firestation savedFirestation = firestationRepository.save(firestation);
        logger.info("Firestation saved with station: {}", savedFirestation.getStation());
        return savedFirestation;
    }

    public Firestation getFirestationByStation(String station) {
        logger.info("Fetching firestation with station: {}", station);
        Firestation firestation = firestationRepository.findByKey(station);
        if (firestation == null) {
            logger.error("Firestation not found with station: {}", station);
            throw new RuntimeException("Firestation not found");
        }
        logger.info("Firestation found with station: {}", station);

        return firestation;
    }

    public void deleteFirestation(String station) {
        logger.info("Deleting firestation with station: {}", station);
        Firestation firestation = firestationRepository.findByKey(station);
        if (firestation == null) {
            logger.error("Firestation not found with station: {}", station);
            throw new RuntimeException("Firestation not found");
        }
        firestationRepository.delete(firestation);
        logger.info("Firestation deleted with station: {}", station);
    }

    public Firestation updateFirestation(Firestation updatedFirestation) {
        logger.info("Updating firestation with station number: {}", updatedFirestation.getStation());
        Firestation existingFirestation = firestationRepository.findByKey(updatedFirestation.getStation());
        if (existingFirestation == null) {
            logger.error("Firestation to update not found with station number: {}", updatedFirestation.getStation());
            throw new RuntimeException("Firestation to update not found");
        }

        existingFirestation.setStation(updatedFirestation.getStation());

        Firestation updated = firestationRepository.save(existingFirestation);
        logger.info("Firestation updated with station number: {}", updated.getStation());

        return updated;
    }
}
