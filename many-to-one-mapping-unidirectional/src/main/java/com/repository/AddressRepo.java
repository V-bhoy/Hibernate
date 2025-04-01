package com.repository;

import com.entity.Address;
import com.entity.Person;

import java.util.List;

public interface AddressRepo {
    void saveAddress(List<Address> addresses);
    void updateAddress(Address address, int addressId);
    void deleteAddress(int addressId);
    Address getAddressById(int addressId);
}
