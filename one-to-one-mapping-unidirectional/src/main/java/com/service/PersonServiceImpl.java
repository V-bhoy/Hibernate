package com.service;

import com.repository.PersonRepo;
import com.entity.Person;

public class PersonServiceImpl implements PersonService {
    private PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public void registerPersonDetails(Person person) {
      try{
          personRepo.registerPersonDetails(person);
      }catch (Exception e){
          throw new RuntimeException("Failed to register person details!", e);
      }
    }

    @Override
    public Person getPersonDetailsById(int id) throws Exception {
        return personRepo.getPersonDetailsById(id);
    }

    @Override
    public void updatePersonDetails(Person person, int personId) {
       try {
           personRepo.updatePersonDetails(person, personId);
       }catch (Exception e){
           throw new RuntimeException("Failed to update person details!", e);
       }
    }

    @Override
    public void deletePersonDetails(int id) {
        try {
            personRepo.deletePersonDetails(id);
        }catch (Exception e){
            throw new RuntimeException("Failed to delete person details!", e);
        }
    }
}
