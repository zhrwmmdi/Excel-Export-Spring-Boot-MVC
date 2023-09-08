package com.example.demo.service;

import com.example.demo.dao.PersonDAO;
import com.example.demo.entity.Person;
import com.example.demo.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl  implements PersonService {
    //define PersonDAO
    private PersonDAO personDAO;
    //constructor injection for PersonDAO object
    @Autowired
    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    @Transactional
    public Person save(Person thePerson) {
        return personDAO.save(thePerson);
    }

    @Override
    public Person getPersonById(int theId) {
        Optional<Person> thePerson = personDAO.findById(theId);
        if (thePerson.isPresent()) {
            return thePerson.get();
        }else {
            throw new PersonNotFoundException("PERSON NOT FOUND!");
        }
    }

    @Override
    @Transactional
    public void deletePersonById(int theId) {
        personDAO.deleteById(getPersonById(theId).getId());
    }

    @Override
    @Transactional
    public void updatePerson(Person updatedPerson) {
        personDAO.save(updatedPerson);
    }
}
