package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecord> getAll() {
        logger.info("Get all medical records");
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalRecord create(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Create medical record: {}", medicalRecord);
        return medicalRecordService.save(medicalRecord);
    }

    @GetMapping("/{firstname}/{lastname}")
    public MedicalRecord get(@PathVariable String firstname, @PathVariable String lastname) {
        logger.info("Get medical record by name: {} {}", firstname, lastname);
        return medicalRecordService.getMedicalRecordByKey(firstname, lastname);
    }

    @PutMapping("/update")
    public MedicalRecord update(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Update medical record with name: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecordService.updateMedicalrecord(medicalRecord);
    }

    @DeleteMapping("/{firstname}/{lastname}")
    public void delete(@PathVariable String firstname, @PathVariable String lastname) {
        logger.info("Delete medical record by name: {} {}", firstname, lastname);
        medicalRecordService.deleteMedicalRecord(firstname, lastname);
    }


}
