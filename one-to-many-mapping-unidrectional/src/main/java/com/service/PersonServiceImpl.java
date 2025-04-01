package com.service;

import com.entity.Address;
import com.entity.Person;
import com.repository.PersonRepo;

import java.util.List;

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
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public void registerAddress(List<Address> addresses, int personId) {
        try{
            personRepo.registerAddress(addresses, personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public void updatePerson(Person person, int personId) {
        try{
           personRepo.updatePerson(person, personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public void updateAddress(Address address, int personId, int addressId) {
        try{
           personRepo.updateAddress(address, personId, addressId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public void deletePerson(int personId) {
        try{
           personRepo.deletePerson(personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public void removeAddresses(List<Address> addresses, int personId) {
        try{
            personRepo.removeAddresses(addresses, personId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in service handler: ",e);
        }
    }

    @Override
    public Person getPerson(int personId) {
        return personRepo.getPerson(personId);
    }
}
