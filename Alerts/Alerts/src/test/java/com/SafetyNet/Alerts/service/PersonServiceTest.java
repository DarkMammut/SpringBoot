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

    @Test
    public void testGetPersons() {
        Person person1 = new Person("John", "Doe", "john@example.com");
        Person person2 = new Person("Jane", "Doe", "jane@example.com");
        List<Person> persons = Arrays.asList(person1, person2);

        when(personRepository.getAll()).thenReturn(persons);

        List<Person> result = personService.getPersons();
        assertEquals(2, result.size());
        assertEquals(persons, result);
    }

    @Test
    public void testSave() {
        Person person = new Person("John", "Doe", "john@example.com");

        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.save(person);
        assertEquals(person, result);
    }

    @Test
    public void testGetPersonByEmail() {
        Person person = new Person("John", "Doe", "john@example.com");

        when(personRepository.findByKey("john@example.com")).thenReturn(person);

        Person result = personService.getPersonByEmail("john@example.com");
        assertEquals(person, result);
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person("John", "Doe", "john@example.com");

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

        assertEquals("person not found", exception.getMessage());
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person("John", "Doe", "john@example.com");

        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.updatePerson(person);
        assertEquals(person, result);
    }
}
