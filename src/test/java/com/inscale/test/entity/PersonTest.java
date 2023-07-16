package com.inscale.test.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PersonTest {

    @Test
    void getId() {
        Person person = new Person();
        person.setId(1L);
        assertEquals(1L, person.getId());
    }

    @Test
    void getName() {
        Person person = new Person();
        person.setName("Test Fonseca");
        assertEquals("Test Fonseca", person.getName());
    }

    @Test
    void getEmail() {
        Person person = new Person();
        person.setEmail("tfonseca@test.com");
        assertEquals("tfonseca@test.com", person.getEmail());
    }

    @Test
    void getAddress() {
        Set<Address> addresses = new HashSet<>();
        Address address = mock(Address.class);
        addresses.add(address);

        Person person = new Person();
        person.setAddress(addresses);
        assertEquals(addresses, person.getAddress());
    }

    @Test
    void getNumber() {
        Person person = new Person();
        person.setNumber("1234567890");
        assertEquals("1234567890", person.getNumber());
    }

    @Test
    void getIdentificationNumber() {
        Person person = new Person();
        person.setIdentificationNumber("ABC123");
        assertEquals("ABC123", person.getIdentificationNumber());
    }

    @Test
    void setId() {
        Person person = new Person();
        person.setId(1L);
        assertEquals(1L, person.getId());
    }

    @Test
    void setName() {
        Person person = new Person();
        person.setName("Test Fonseca");
        assertEquals("Test Fonseca", person.getName());
    }

    @Test
    void setEmail() {
        Person person = new Person();
        person.setEmail("tfonseca@test.com");
        assertEquals("tfonseca@test.com", person.getEmail());
    }

    @Test
    void setAddress() {
        Set<Address> addresses = new HashSet<>();
        Address address = mock(Address.class);
        addresses.add(address);

        Person person = new Person();
        person.setAddress(addresses);
        assertEquals(addresses, person.getAddress());
    }

    @Test
    void setNumber() {
        Person person = new Person();
        person.setNumber("1234567890");
        assertEquals("1234567890", person.getNumber());
    }

    @Test
    void setIdentificationNumber() {
        Person person = new Person();
        person.setIdentificationNumber("ABC123");
        assertEquals("ABC123", person.getIdentificationNumber());
    }
}