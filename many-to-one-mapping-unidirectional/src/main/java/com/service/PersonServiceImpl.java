package com.service;

import com.entity.Person;
import com.repository.PersonRepo;

public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public void savePerson(Person person) {
        try{
            personRepo.savePerson(person);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in person service handler",e);
        }
    }

    @Override
    public void updatePerson(Person person, int personId) {
        try{
            personRepo.updatePerson(person, personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in person service handler",e);
        }
    }

    @Override
    public Person getPersonById(int personId) {
        try{
            return personRepo.getPersonById(personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in person service handler",e);
        }
    }
}
