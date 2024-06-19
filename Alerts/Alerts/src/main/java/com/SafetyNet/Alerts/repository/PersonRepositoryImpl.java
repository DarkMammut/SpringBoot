package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.util.JsonDataImporter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> persons = JsonDataImporter.persons();

    @Override
    public List<Person> findByAddress(String address) {
        System.out.println(persons);
        return persons.stream()
                .filter(personAddress -> personAddress.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByFirstnameAndLastname(String firstname, String lastname) {
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstname)
                        && person.getLastName().equals(lastname))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> findByCity(String city) {
        return persons.stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }


    @Override
    public List<Person> getAll() {
        return persons;
    }

    @Override
    public Person update(Person updatedPerson) {

        Person existingPerson = findByKey(updatedPerson.getEmail());
        if (existingPerson == null) {
            throw new RuntimeException("person not found");
        }

        // Update the fields of the existing person with the new values
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setAddress(updatedPerson.getAddress());
        existingPerson.setCity(updatedPerson.getCity());
        existingPerson.setZip(updatedPerson.getZip());
        existingPerson.setPhone(updatedPerson.getPhone());

        return existingPerson;
    }

    @Override
    public Person save(Person person) {
        persons.add(person);
        return person;
    }

    @Override
    public Person findByKey(String key) {
        return persons.stream()
                .filter(person -> person.getEmail().equals(key))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Person person) {
        persons.remove(person);
    }
}
