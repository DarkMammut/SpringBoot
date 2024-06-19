package com.SafetyNet.Alerts.service;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    Logger logger = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        logger.info("Fetching all persons");
        List<Person> persons = personRepository.getAll();
        logger.info("Number of persons fetched: {}", persons.size());
        return persons;
    }

    public Person save(Person person) {
        logger.info("Saving person with email: {}", person.getEmail());
        return personRepository.save(person);
    }

    public Person getPersonByEmail(String email) {
        logger.info("Fetching person with email: {}", email);
        Person person = personRepository.findByKey(email);
        if (person == null) {
            logger.error("Person not found with email: {}", email);
            throw new RuntimeException("Person not found");
        }
        return person;
    }

    public void deletePerson(String email) {
        logger.info("Deleting person with email: {}", email);
        Person person = personRepository.findByKey(email);
        if (person == null) {
            logger.error("Person to delete not found with email: {}", email);
            throw new RuntimeException("Person not found");
        }
        personRepository.delete(person);
        logger.info("Person deleted with email: {}", email);
    }

    public Person updatePerson(Person updatedPerson) {
        logger.info("Updating person with email: {}", updatedPerson.getEmail());
        Person existingPerson = personRepository.findByKey(updatedPerson.getEmail());
        if (existingPerson == null) {
            logger.error("Person to update not found with email: {}", updatedPerson.getEmail());
            throw new RuntimeException("Person to update not found");
        }

        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setCity(updatedPerson.getCity());
        existingPerson.setZip(updatedPerson.getZip());
        existingPerson.setPhone(updatedPerson.getPhone());

        Person updated = personRepository.save(existingPerson);
        logger.info("Person updated with email: {}", updated.getEmail());
        return updated;
    }
}
