package com.SafetyNet.Alerts.repository;

import com.SafetyNet.Alerts.model.Person;

import java.util.List;

//@Repository
public interface PersonRepository extends CrudRepository<Person> {
    List<Person> findByAddress(String address);
    List<Person> findByFirstnameAndLastname(String firstname, String lastname);
    List<Person> findByCity(String city);
}
