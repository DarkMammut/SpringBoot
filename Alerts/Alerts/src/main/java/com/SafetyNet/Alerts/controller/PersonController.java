package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAll() {
        logger.info("Get all persons");
        return personService.getPersons();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        logger.info("Create person: {}", person);
        return personService.save(person);
    }

    @GetMapping("/{email}")
    public Person get(@PathVariable String email) {
        logger.info("Get person by email: {}", email);
        return personService.getPersonByEmail(email);
    }

    @PutMapping("/update")
    public Person update(@RequestBody Person person) {
        logger.info("Update person with email: {}", person.getEmail());
            return personService.updatePerson(person);
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        logger.info("Delete person by email: {}", email);
        personService.deletePerson(email);
    }
}
