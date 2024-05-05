package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.util.JsonDataImporter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final JsonDataImporter jsonDataImporter;

    private List<Person> persons = new ArrayList<>();

    private PersonRepositoryImpl(JsonDataImporter jsonDataImporter) {
        this.jsonDataImporter = jsonDataImporter;
        persons = JsonDataImporter.persons();
    };

    @Override
    public List<Person> getAll() {
        return persons;
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
                .toList()
                .get(0);
    }

    @Override
    public void delete(Person person) {
        persons.remove(person);
    }
}
