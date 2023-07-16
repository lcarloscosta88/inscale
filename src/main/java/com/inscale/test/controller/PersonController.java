package com.inscale.test.controller;

import com.inscale.test.dto.PersonDTO;
import com.inscale.test.entity.Person;
import com.inscale.test.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @Operation(summary = "Create a new person")
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO personDTO) throws Exception {
        Person createdPerson = personService.createPerson(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    @GetMapping
    @Operation(summary = "Get all people")
    public ResponseEntity<List<Person>> getAllPeople() {
        List<Person> people = personService.getAllPeople();
        return ResponseEntity.ok(people);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

    @PatchMapping()
    @Operation(summary = "Update a person")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonDTO personDTO) throws Exception {
        Person updatedPerson = personService.updatePerson(personDTO);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
