package com.SafetyNet.Alerts.controller;

import com.SafetyNet.Alerts.model.Person;
import com.SafetyNet.Alerts.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    private List<Person> getPersons() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setEmail("john@example.com");

        Person person2 = new Person();
        person2.setFirstName("Dan");
        person2.setLastName("Van");
        person2.setEmail("dan@van.com");

        return Arrays.asList(person1, person2);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Person> persons = getPersons();

        when(personService.getPersons()).thenReturn(persons);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].firstName").value("Dan"))
                .andExpect(jsonPath("$[1].lastName").value("Van"))
                .andExpect(jsonPath("$[1].email").value("dan@van.com"));

        verify(personService).getPersons();
    }

    @Test
    public void testCreate() throws Exception {
        Person person = getPersons().get(0);
        String json = """
                    {
                        "firstName": "John",
                        "lastName": "Doe",
                        "email": "john@example.com"
                    }
                """;

        when(personService.save(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

//        verify(personService).save(person);
    }

    @Test
    public void testGet() throws Exception {
        Person person = getPersons().get(0);

        when(personService.getPersonByEmail("john@example.com")).thenReturn(person);

        mockMvc.perform(get("/persons/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(personService).getPersonByEmail("john@example.com");
    }

    @Test
    public void testUpdate() throws Exception {
        Person person = getPersons().get(0);
        String json = """
                    {
                        "firstName": "John",
                        "lastName": "Doe",
                        "email": "john@example.com"
                    }
                """;

        when(personService.updatePerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(put("/persons/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

//        verify(personService).updatePerson(person);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/persons/john@example.com"))
                .andExpect(status().isOk());

        verify(personService).deletePerson("john@example.com");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
