package com.repository;

import com.entity.Person;

public interface PersonRepo {
    void savePerson(Person person);
    void updatePerson(Person person, int personId);
    // deleting person is not a good practice because it might cause
    // inconsistent data in child entity
    // you need to manually delete all the references or make it null
    // before deleting person
    Person getPersonById(int personId);
}
