package com.inscale.test.controller;
import com.inscale.test.controller.PersonController;
import com.inscale.test.dto.PersonDTO;
import com.inscale.test.entity.Person;
import com.inscale.test.services.PersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    private PersonController personController;

    public PersonControllerTest() {
        MockitoAnnotations.openMocks(this);
        personController = new PersonController(personService);
    }

    @Test
    void createPerson() throws Exception {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Test Fonseca");

        Person createdPerson = new Person();
        createdPerson.setId(1L);
        createdPerson.setName("Test Fonseca");

        when(personService.createPerson(personDTO)).thenReturn(createdPerson);

        // Act
        ResponseEntity<Person> response = personController.createPerson(personDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPerson, response.getBody());

        verify(personService, times(1)).createPerson(personDTO);
    }

    @Test
    void getAllPeople() {
        // Arrange
        Person person = new Person();
        person.setId(1L);
        person.setName("Test Fonseca");

        List<Person> people = Collections.singletonList(person);

        when(personService.getAllPeople()).thenReturn(people);

        // Act
        ResponseEntity<List<Person>> response = personController.getAllPeople();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(people, response.getBody());

        verify(personService, times(1)).getAllPeople();
    }

    @Test
    void getPersonById() {
        // Arrange
        long personId = 1L;
        Person person = new Person();
        person.setId(personId);
        person.setName("Test Fonseca");

        when(personService.getPersonById(personId)).thenReturn(person);

        // Act
        ResponseEntity<Person> response = personController.getPersonById(personId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(person, response.getBody());

        verify(personService, times(1)).getPersonById(personId);
    }

    @Test
    void updatePerson() throws Exception {
        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("Updated Name");

        Person updatedPerson = new Person();
        updatedPerson.setId(1L);
        updatedPerson.setName("Updated Name");

        when(personService.updatePerson(personDTO)).thenReturn(updatedPerson);

        // Act
        ResponseEntity<Person> response = personController.updatePerson(personDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPerson, response.getBody());

        verify(personService, times(1)).updatePerson(personDTO);
    }

    @Test
    void deletePerson() {
        // Arrange
        long personId = 1L;

        // Act
        ResponseEntity<Void> response = personController.deletePerson(personId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(personService, times(1)).deletePerson(personId);
    }
}
