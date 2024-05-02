package com.SafetyNet.Alerts.model;

import java.util.List;

public class PersonList {
    private List<Person> persons;

    public PersonList(){}
    public PersonList(List<Person> persons){
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "persons=" + persons +
                '}';
    }
}
