package com.SafetyNet.Alerts.model;

import java.util.List;

public class PersonsList {
    private List<Person> people;

    public PersonsList() {
    }

    public PersonsList(List<Person> people) {
        this.people = people;
    }

    public List<Person> getPersons() {
        return people;
    }

    public void setPersons(List<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "PersonsList{" +
                "persons=" + people +
                '}';
    }
}
