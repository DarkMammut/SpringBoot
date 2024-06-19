package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Person> getPersons() {

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setEmail("john@example.com");
        person1.setAddress("123 Street");
        person1.setCity("City");
        person1.setZip("54321");
        person1.setPhone("456-7890-1234");

        Person person2 = new Person();
        person2.setFirstName("Dan");
        person2.setLastName("Van");
        person2.setEmail("Dan@van.com");
        person2.setAddress("456 Street");
        person2.setCity("Lost City");
        person2.setZip("36474");
        person2.setPhone("7890-1234-4567");

        return Arrays.asList(person1, person2);
    }

    @Test
    public void testGetPersons() {
        List<Person> persons = getPersons();

        when(personRepository.getAll()).thenReturn(persons);

        List<Person> result = personService.getPersons();
        assertEquals(2, result.size());
        assertEquals(persons, result);
    }

    @Test
    public void testSave() {
        Person person = getPersons().get(0);

        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.save(person);
        assertEquals(person, result);
    }

    @Test
    public void testGetPersonByEmail() {
        Person person = getPersons().get(0);

        when(personRepository.findByKey("john@example.com")).thenReturn(person);

        Person result = personService.getPersonByEmail("john@example.com");
        assertEquals(person, result);
    }

    @Test
    public void testDeletePerson() {
        Person person = getPersons().get(0);

        when(personRepository.findByKey("john@example.com")).thenReturn(person);
        doNothing().when(personRepository).delete(person);

        personService.deletePerson("john@example.com");

        verify(personRepository, times(1)).delete(person);
    }

    @Test
    public void testDeletePersonNotFound() {
        when(personRepository.findByKey("john@example.com")).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.deletePerson("john@example.com");
        });

        assertEquals("Person not found", exception.getMessage());
    }

    @Test
    public void testUpdatePerson() {
        Person existingPerson = getPersons().get(0);
        Person updatedPerson = new Person();
        updatedPerson.setFirstName("John");
        updatedPerson.setLastName("Doe");
        updatedPerson.setEmail("john@example.com");
        updatedPerson.setAddress("123 New Street");
        updatedPerson.setCity("New City");
        updatedPerson.setZip("12345");
        updatedPerson.setPhone("123-456-7890");

        when(personRepository.findByKey("john@example.com")).thenReturn(existingPerson);
        when(personRepository.save(existingPerson)).thenReturn(existingPerson);

        Person result = personService.updatePerson(updatedPerson);
        assertEquals(updatedPerson.getAddress(), result.getAddress());
        assertEquals(updatedPerson.getCity(), result.getCity());
        assertEquals(updatedPerson.getZip(), result.getZip());
        assertEquals(updatedPerson.getPhone(), result.getPhone());
    }
}
