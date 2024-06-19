package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestations")
public class FirestationController {
    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping
    public List<Firestation> getAll() {
        logger.info("Get all firestations");
        return firestationService.getFirestations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Firestation create(@RequestBody Firestation firestation) {
        logger.info("Create firestation {}", firestation);
        return firestationService.save(firestation);
    }

    @GetMapping("/{station}")
    public Firestation get(@PathVariable String station) {
        logger.info("Get firestation by station {}", station);
        return firestationService.getFirestationByStation(station);
    }

    @PutMapping("/update")
    public Firestation update(@RequestBody Firestation firestation) {
        logger.info("Update firestation with station number: {}", firestation.getStation());
        return firestationService.updateFirestation(firestation);
    }

    @DeleteMapping("/{station}")
    public void delete(@PathVariable String station) {
        logger.info("Delete firestation by station: {}", station);
        firestationService.deleteFirestation(station);
    }
}
