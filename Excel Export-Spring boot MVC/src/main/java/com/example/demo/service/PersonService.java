package com.example.demo.service;

import com.example.demo.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    Person save(Person thePerson);
    Person getPersonById(int theId);
    void deletePersonById(int theId);
    void updatePerson(Person updatedPerson);
}
