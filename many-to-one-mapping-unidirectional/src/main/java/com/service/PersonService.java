package com.service;

import com.entity.Person;

public interface PersonService {
    void savePerson(Person person);
    void updatePerson(Person person, int personId);
    Person getPersonById(int personId);
}
