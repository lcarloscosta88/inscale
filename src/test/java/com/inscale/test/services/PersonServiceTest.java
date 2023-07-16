package com.inscale.test.services;

import com.inscale.test.aws.S3Controller;
import com.inscale.test.aws.SqsController;
import com.inscale.test.dto.AddressDTO;
import com.inscale.test.dto.PersonDTO;
import com.inscale.test.entity.Address;
import com.inscale.test.entity.Person;
import com.inscale.test.repository.AddressRepository;
import com.inscale.test.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private S3Controller s3Controller;

    @Mock
    private SqsController sqsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository, addressRepository, s3Controller, sqsController);
    }

    @Test
    void createPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("Test Fonseca");

        Set<AddressDTO> addressDTOs = new HashSet<>();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Montijo");
        addressDTO.setCountry("Portugal");
        addressDTO.setNumber("2Dto");
        addressDTO.setType(true);
        addressDTOs.add(addressDTO);
        personDTO.setAddressDTOList(addressDTOs);
        
        Set<Address> address = new HashSet<>();

        for (AddressDTO dto : addressDTOs) {
            Address ads = new Address();
            BeanUtils.copyProperties(dto, ads);
            address.add(ads);
        }
        
        
        Person person = new Person();
        person.setId(1L);
        person.setName("Test Fonseca");
        person.setEmail("tfonseca@test.com");
        person.setNumber("999-999-999");
        person.setIdentificationNumber("abc1234567");
        person.setAddress(address);

        when(addressRepository.save(any(Address.class))).thenReturn(address.stream().findFirst().get());
        when(personRepository.save(any(Person.class))).thenReturn(person);

        // Act
        Person createdPerson = personService.createPerson(personDTO);

        // Assert
        assertNotNull(createdPerson);
        assertEquals(person.getId(), createdPerson.getId());
        assertEquals(person.getName(), createdPerson.getName());
        assertEquals(1, createdPerson.getAddress().size());
        assertEquals(address, createdPerson.getAddress().iterator().next());

        verify(s3Controller, times(1)).uploadToS3(person);
        verify(personRepository, times(1)).save(any(Person.class));
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void getAllPeople() {

        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");

        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<Person> people = personService.getAllPersons();

        assertNotNull(people);
        assertEquals(1, people.size());
        assertEquals(person, people.get(0));

        verify(personRepository, times(1)).findAll();
    }

    @Test
    void getPersonById() {
        long personId = 1L;
        Person person = new Person();
        person.setId(personId);
        person.setName("John Doe");

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        Person retrievedPerson = personService.getPersonById(personId);

        assertNotNull(retrievedPerson);
        assertEquals(person.getId(), retrievedPerson.getId());
        assertEquals(person.getName(), retrievedPerson.getName());

        verify(personRepository, times(1)).findById(personId);
    }

    @Test
    void updatePerson() throws Exception {
        long personId = 1L;
        PersonDTO updatedPersonDTO = new PersonDTO();
        updatedPersonDTO.setId(personId);
        updatedPersonDTO.setName("Updated Name");

        Person existingPerson = new Person();
        existingPerson.setId(personId);
        existingPerson.setName("John Doe");

        when(personRepository.findById(personId)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        Person updatedPerson = personService.updatePerson(updatedPersonDTO);

        assertNotNull(updatedPerson);
        assertEquals(personId, updatedPerson.getId());
        assertEquals(updatedPersonDTO.getName(), updatedPerson.getName());

        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void deletePerson() {
        long personId = 1L;
        personService.deletePerson(personId);
        verify(personRepository, times(1)).deleteById(personId);
    }

}
