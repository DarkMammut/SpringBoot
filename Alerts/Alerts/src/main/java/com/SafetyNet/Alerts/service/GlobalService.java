package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FirestationRepository;
import com.SafetyNet.Alerts.repository.MedicalRecordRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class DTO {

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public DTO(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<Person> getPersonsByStationNumber(String station) {
        List<Firestation> firestations = (List<Firestation>) firestationRepository.findByKey(station);
        List<String> addresses = firestations.stream().map(Firestation::getAddress).collect(Collectors.toList());
        return personRepository.findByAddressIn(addresses);
    }

    public long countAdults(List<Person> persons) {
        return persons.stream().filter(this::isAdult).count();
    }

    public long countChildren(List<Person> persons) {
        return persons.stream().filter(person -> !isAdult(person)).count();
    }

    private boolean isAdult(Person person) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (medicalRecord == null) {
            return false;
        }
        LocalDate birthdate = medicalRecord.getBirthdate();
        LocalDate today = LocalDate.now();
        return Period.between(birthdate, today).getYears() >= 18;
    }
}
