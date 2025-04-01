package com.service;

import com.entity.Address;
import com.entity.Person;

import java.util.List;

public interface PersonService {
    void savePerson(Person person);
    void registerAddress(List<Address> addresses, int personId);
    void updatePerson(Person person, int personId);
    void updateAddress(Address address, int personId, int addressId);
    void deletePerson(int personId);
    void removeAddresses(List<Address> addresses, int personId);
    Person getPerson(int personId);
}
