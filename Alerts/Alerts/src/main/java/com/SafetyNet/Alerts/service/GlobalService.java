package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FirestationRepository;
import com.SafetyNet.Alerts.repository.MedicalRecordRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;
import com.SafetyNet.Alerts.service.dto.*;
import com.SafetyNet.Alerts.util.DateFormater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GlobalService {

    Logger logger = LoggerFactory.getLogger(GlobalService.class);

    private final PersonRepository personRepository;
    private final FirestationRepository firestationRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PersonService personService;

    public GlobalService(PersonRepository personRepository, FirestationRepository firestationRepository, MedicalRecordRepository medicalRecordRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.firestationRepository = firestationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personService = personService;
    }

    public ListPersonMedicalRecordDTO getPersonsByStationNumber(String station) {
        logger.info("Getting persons by station number: {}", station);
        List<Firestation> firestations = firestationRepository.findByStationNumber(station);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        List<Person> persons = personRepository.getAll();
        List<String> addresses = firestations.stream().map(Firestation::getAddress).toList();

        List<PersonMedicalRecordDTO> personMedicalRecordDTOs = new ArrayList<>();
        AtomicInteger adult = new AtomicInteger();
        AtomicInteger children = new AtomicInteger();

        for (String address : addresses) {
            persons.stream().filter(person -> person.getAddress().equals(address)).findFirst().ifPresent(person -> {
                medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                        && medicalRecord.getLastName().equals(person.getLastName())).findFirst().ifPresent(medicalRecord -> {
                    PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO();
                    personMedicalRecordDTO.setFirstName(medicalRecord.getFirstName());
                    personMedicalRecordDTO.setLastName(medicalRecord.getLastName());
                    personMedicalRecordDTO.setAddress(address);
                    personMedicalRecordDTO.setPhoneNumber(person.getPhone());
                    LocalDate localDate = DateFormater.stringToLocalDate(medicalRecord.getBirthdate());
                    int age = Period.between(localDate, LocalDate.now()).getYears();
                    if (age >= 18) {
                        adult.getAndIncrement();
                    } else {
                        children.getAndIncrement();
                    }
                    personMedicalRecordDTOs.add(personMedicalRecordDTO);
                });
            });
        }

        ListPersonMedicalRecordDTO listPersonMedicalRecordDTO = new ListPersonMedicalRecordDTO();
        listPersonMedicalRecordDTO.setPersonMedicalRecordDTOs(personMedicalRecordDTOs);
        listPersonMedicalRecordDTO.setAdult(adult.get());
        listPersonMedicalRecordDTO.setChildren(children.get());

        logger.info("Returning list of persons by station number: {}", station);
        return listPersonMedicalRecordDTO;
    }

    public ListChildAddressDTO getChildByAddress(String address) {
        logger.info("Getting children by address: {}", address);
        List<Person> persons = personRepository.findByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();

        List<ChildAddressDTO> childAddressDTOs = new ArrayList<>();
        List<HouseholdMemberDTO> household = new ArrayList<>();

        for (Person person : persons) {
            medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())).findFirst().ifPresent(medicalRecord -> {
                LocalDate localDate = DateFormater.stringToLocalDate(medicalRecord.getBirthdate());
                int age = Period.between(localDate, LocalDate.now()).getYears();
                if (age < 18) {
                    ChildAddressDTO childAddressDTO = new ChildAddressDTO();
                    childAddressDTO.setFirstName(medicalRecord.getFirstName());
                    childAddressDTO.setLastName(medicalRecord.getLastName());
                    childAddressDTO.setAge(age);
                    childAddressDTOs.add(childAddressDTO);
                } else {
                    HouseholdMemberDTO householdMemberDTO = new HouseholdMemberDTO();
                    householdMemberDTO.setFirstName(person.getFirstName());
                    householdMemberDTO.setLastName(person.getLastName());
                    householdMemberDTO.setAge(age);
                    household.add(householdMemberDTO);
                }
            });
        }

        ListChildAddressDTO listChildAddressDTO = new ListChildAddressDTO();
        listChildAddressDTO.setChildAddressDTOs(childAddressDTOs);
        listChildAddressDTO.setHousehold(household);

        logger.info("Returning list of children by address: {}", address);
        return listChildAddressDTO;
    }

    public ListPhoneNumberStationDTO getPhoneNumberByStationNumber(String station) {
        logger.info("Getting phone numbers by station number: {}", station);
        List<Firestation> firestations = firestationRepository.findByStationNumber(station);
        List<Person> persons = personRepository.getAll();
        List<String> addresses = firestations.stream().map(Firestation::getAddress).toList();

        List<PhoneNumberStationDTO> phoneNumberStationDTOs = new ArrayList<>();

        for (String address : addresses) {
            persons.stream().filter(person -> person.getAddress().equals(address)).findFirst().ifPresent(person -> {
                PhoneNumberStationDTO phoneNumberStationDTO = new PhoneNumberStationDTO();
                phoneNumberStationDTO.setPhoneNumber(person.getPhone());
                phoneNumberStationDTOs.add(phoneNumberStationDTO);
            });
        }

        ListPhoneNumberStationDTO listPhoneNumberStationDTO = new ListPhoneNumberStationDTO();
        listPhoneNumberStationDTO.setPhoneNumberStationDTOs(phoneNumberStationDTOs);

        logger.info("Returning list of phone numbers by station number: {}", station);
        return listPhoneNumberStationDTO;
    }

    public ListPersonAddressDTO getPersonByAddress(String address) {
        logger.info("Getting persons by address: {}", address);
        List<Person> persons = personRepository.findByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        List<Firestation> firestations = firestationRepository.getAll();

        List<PersonAddressDTO> personAddressDTOs = new ArrayList<>();

        for (Person person : persons) {
            PersonAddressDTO personAddressDTO = new PersonAddressDTO();
            personAddressDTO.setPhoneNumber(person.getPhone());
            medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())).findFirst().ifPresent(medicalRecord -> {
                LocalDate localDate = DateFormater.stringToLocalDate(medicalRecord.getBirthdate());
                int age = Period.between(localDate, LocalDate.now()).getYears();
                personAddressDTO.setFirstName(medicalRecord.getFirstName());
                personAddressDTO.setLastName(medicalRecord.getLastName());
                personAddressDTO.setAge(age);
                personAddressDTO.setMedications(medicalRecord.getMedications());
                personAddressDTO.setAllergies(medicalRecord.getAllergies());
                firestations.stream().filter(firestation -> firestation.getAddress().equals(person.getAddress())).findFirst().ifPresent(firestation -> {
                    personAddressDTO.setStation(firestation.getStation());
                    personAddressDTOs.add(personAddressDTO);
                });
            });
        }

        ListPersonAddressDTO listPersonAddressDTO = new ListPersonAddressDTO();
        listPersonAddressDTO.setPersonAddressDTO(personAddressDTOs);

        logger.info("Returning list of persons by address: {}", address);
        return listPersonAddressDTO;
    }

    public ListHouseholdStationDTO getHouseholdByStationNumber(List<String> stationNumbers) {
        logger.info("Getting households by station numbers: {}", stationNumbers);
        List<Firestation> firestations = firestationRepository.findByStationNumbers(stationNumbers);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        List<Person> persons = personRepository.getAll();
        List<String> addresses = firestations.stream().map(Firestation::getAddress).toList();

        List<HouseholdDTO> households = new ArrayList<>();

        for (String address : addresses) {
            List<Person> personsAtAddress = persons.stream().filter(person -> person.getAddress().equals(address)).toList();

            List<PersonHouseholdDTO> personHouseholdDTOs = new ArrayList<>();

            for (Person person : personsAtAddress) {
                medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                        && medicalRecord.getLastName().equals(person.getLastName())).findFirst().ifPresent(medicalRecord -> {
                    PersonHouseholdDTO personHouseholdDTO = new PersonHouseholdDTO();
                    personHouseholdDTO.setFirstname(medicalRecord.getFirstName());
                    personHouseholdDTO.setLastname(medicalRecord.getLastName());
                    personHouseholdDTO.setAddress(address);
                    personHouseholdDTO.setPhone(person.getPhone());
                    LocalDate localDate = DateFormater.stringToLocalDate(medicalRecord.getBirthdate());
                    int age = Period.between(localDate, LocalDate.now()).getYears();
                    personHouseholdDTO.setAge(age);
                    personHouseholdDTO.setMedications(medicalRecord.getMedications());
                    personHouseholdDTO.setAllergies(medicalRecord.getAllergies());
                    personHouseholdDTOs.add(personHouseholdDTO);
                });
            }

            if (!personHouseholdDTOs.isEmpty()) {
                HouseholdDTO householdDTO = new HouseholdDTO();
                householdDTO.setAddress(address);
                householdDTO.setPersons(personHouseholdDTOs);
                households.add(householdDTO);
            }
        }

        ListHouseholdStationDTO listHouseholdStationDTO = new ListHouseholdStationDTO();
        listHouseholdStationDTO.setHouseholds(households);

        logger.info("Returning list of households by station numbers: {}", stationNumbers);
        return listHouseholdStationDTO;
    }

    public ListPersonDTO getPersonInfo(String firstName, String lastName) {
        logger.info("Getting person info for: {} {}", firstName, lastName);
        List<Person> persons = personRepository.findByFirstnameAndLastname(firstName, lastName);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();

        List<PersonDTO> personDTOs = new ArrayList<>();

        for (Person person : persons) {
            medicalRecords.stream().filter(medicalRecord -> medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())).findFirst().ifPresent(medicalRecord -> {
                PersonDTO personDTO = new PersonDTO();
                personDTO.setFirstName(person.getFirstName());
                personDTO.setLastName(person.getLastName());
                LocalDate localDate = DateFormater.stringToLocalDate(medicalRecord.getBirthdate());
                int age = Period.between(localDate, LocalDate.now()).getYears();
                personDTO.setAge(age);
                personDTO.setEmail(person.getEmail());
                personDTO.setMedications(medicalRecord.getMedications());
                personDTO.setAllergies(medicalRecord.getAllergies());
                personDTOs.add(personDTO);
            });
        }

        ListPersonDTO listPersonDTO = new ListPersonDTO();
        listPersonDTO.setPersonDTO(personDTOs);

        logger.info("Returning person info for: {} {}", firstName, lastName);
        return listPersonDTO;
    }

    public ListEmailCityDTO getEmailByCity(String city) {
        logger.info("Getting emails by city: {}", city);
        List<Person> persons = personRepository.findByCity(city);

        List<EmailCityDTO> emailCityDTOs = new ArrayList<>();

        for (Person person : persons) {
            EmailCityDTO emailCityDTO = new EmailCityDTO();
            emailCityDTO.setEmail(person.getEmail());
            emailCityDTOs.add(emailCityDTO);
        }

        ListEmailCityDTO listEmailCityDTO = new ListEmailCityDTO();
        listEmailCityDTO.setEmailCityDTO(emailCityDTOs);

        logger.info("Returning emails by city: {}", city);
        return listEmailCityDTO;
    }
}

