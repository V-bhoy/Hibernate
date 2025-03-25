package com.repository;

import com.entity.Person;

public interface PersonRepo {
    void registerPersonDetails(Person person);
    Person getPersonDetailsById(int id) throws Exception;
    void updatePersonDetails(Person person, int personId);
    void deletePersonDetails(int id);
}
