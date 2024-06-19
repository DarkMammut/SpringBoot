package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Firestation;
import com.SafetyNet.Alerts.model.MedicalRecord;
import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.FirestationRepository;
import com.SafetyNet.Alerts.repository.MedicalRecordRepository;
import com.SafetyNet.Alerts.repository.PersonRepository;
import com.SafetyNet.Alerts.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class GlobalServiceTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private FirestationRepository firestationRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private GlobalService globalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Person> getPersons() {

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setEmail("john@example.com");
        person1.setAddress("1509 Culver St");
        person1.setPhone("123-456-7890");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Dee");
        person2.setEmail("Jane-Doe@example.com");
        person2.setAddress("123 Test St");
        person2.setPhone("098-765-4321");

        Person person3 = new Person();
        person3.setFirstName("Kevin");
        person3.setLastName("Dee");
        person3.setEmail("Kevin-Doe@example.com");
        person3.setAddress("123 Test St");
        person3.setPhone("098-765-5367");


        return Arrays.asList(person1, person2, person3);
    }

    private List<Firestation> getFirestations() {

        Firestation firestation1 = new Firestation();
        firestation1.setAddress("1509 Culver St");
        firestation1.setStation("1");

        Firestation firestation2 = new Firestation();
        firestation2.setAddress("123 Test St");
        firestation2.setStation("2");

        return Arrays.asList(firestation1, firestation2);
    }

    private List<MedicalRecord> getMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/08/1990");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Dee");
        medicalRecord2.setBirthdate("08/01/2010");

        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setFirstName("Kevin");
        medicalRecord3.setLastName("Dee");
        medicalRecord3.setBirthdate("05/17/1992");

        return Arrays.asList(medicalRecord1, medicalRecord2, medicalRecord3);
    }

    @Test
    public void testGetPersonsByStationNumber() {
        List<Firestation> firestations = getFirestations();
        List<Person> persons = getPersons();
        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(firestationRepository.findByStationNumber("1")).thenReturn(Collections.singletonList(firestations.get(0)));
        when(personRepository.getAll()).thenReturn(persons);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        ListPersonMedicalRecordDTO result = globalService.getPersonsByStationNumber("1");

        assertNotNull(result);
        assertEquals(1, result.getPersonMedicalRecordDTOs().size());
        assertEquals(1, result.getAdult());
        assertEquals(0, result.getChildren());

        PersonMedicalRecordDTO dto = result.getPersonMedicalRecordDTOs().get(0);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("1509 Culver St", dto.getAddress());
        assertEquals("123-456-7890", dto.getPhoneNumber());
    }

    @Test
    public void testGetChildByAddress() {

        List<Person> persons = getPersons();
        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(personRepository.findByAddress("123 Test St")).thenReturn(Arrays.asList(persons.get(1), persons.get(2)));
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        ListChildAddressDTO result = globalService.getChildByAddress("123 Test St");

        assertNotNull(result);
        assertEquals(1, result.getChildAddressDTOs().size());
        assertEquals("Jane", result.getChildAddressDTOs().get(0).getFirstName());
        assertEquals("Dee", result.getChildAddressDTOs().get(0).getLastName());
        assertEquals(13, result.getChildAddressDTOs().get(0).getAge());  // Assuming the current year is 2024

        assertEquals(1, result.getHousehold().size());
        assertEquals("Kevin", result.getHousehold().get(0).getFirstName());
        assertEquals("Dee", result.getHousehold().get(0).getLastName());
    }

    @Test
    public void testGetPhoneNumberByStationNumber() {
        List<Person> persons = getPersons();
        List<Firestation> firestations = getFirestations();

        when(firestationRepository.findByStationNumber("1")).thenReturn(Collections.singletonList(firestations.get(0)));
        when(personRepository.getAll()).thenReturn(persons);

        ListPhoneNumberStationDTO result = globalService.getPhoneNumberByStationNumber("1");

        assertNotNull(result);
        assertEquals(1, result.getPhoneNumberStationDTOs().size());
        assertEquals("123-456-7890", result.getPhoneNumberStationDTOs().get(0).getPhoneNumber());
    }

    @Test
    public void testGetPersonByAddress() {
        List<Person> persons = getPersons();
        List<MedicalRecord> medicalRecords = getMedicalRecords();
        List<Firestation> firestations = getFirestations();

        when(personRepository.findByAddress("1509 Culver St")).thenReturn(Collections.singletonList(persons.get(0)));
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);
        when(firestationRepository.getAll()).thenReturn(firestations);

        ListPersonAddressDTO result = globalService.getPersonByAddress("1509 Culver St");

        assertNotNull(result);
        assertEquals(1, result.getPersonAddressDTO().size());
        assertEquals("John", result.getPersonAddressDTO().get(0).getFirstName());
        assertEquals("Doe", result.getPersonAddressDTO().get(0).getLastName());
    }

    @Test
    public void testGetHouseholdByStationNumber() {
        List<Person> persons = getPersons();
        List<Firestation> firestations = getFirestations();
        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(firestationRepository.findByStationNumbers(Arrays.asList("1", "2"))).thenReturn(firestations);
        when(personRepository.getAll()).thenReturn(persons);
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        ListHouseholdStationDTO result = globalService.getHouseholdByStationNumber(Arrays.asList("1", "2"));

        assertNotNull(result);
        assertEquals(2, result.getHouseholds().size());
    }

    @Test
    public void testGetPersonInfo() {
        List<Person> persons = getPersons();
        List<MedicalRecord> medicalRecords = getMedicalRecords();

        when(personRepository.findByFirstnameAndLastname("Jane", "Dee")).thenReturn(Collections.singletonList(persons.get(1)));
        when(medicalRecordRepository.getAll()).thenReturn(medicalRecords);

        ListPersonDTO result = globalService.getPersonInfo("Jane", "Dee");

        assertNotNull(result);
        assertEquals(1, result.getPersonDTO().size());
        assertEquals("Jane", result.getPersonDTO().get(0).getFirstName());
        assertEquals("Dee", result.getPersonDTO().get(0).getLastName());
    }

    @Test
    public void testGetEmailByCity() {
        List<Person> persons = getPersons();

        when(personRepository.findByCity("SomeCity")).thenReturn(persons);

        ListEmailCityDTO result = globalService.getEmailByCity("SomeCity");

        assertNotNull(result);
        assertEquals(3, result.getEmailCityDTO().size());
    }
}
