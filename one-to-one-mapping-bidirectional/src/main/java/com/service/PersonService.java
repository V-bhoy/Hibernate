package com.service;

import com.entity.Person;

public interface PersonService {
    void registerPersonDetails(Person person);
    Person getPersonDetailsById(int id);
    void updatePersonDetails(Person person, int personId);
    void deletePersonDetails(int id);
}
