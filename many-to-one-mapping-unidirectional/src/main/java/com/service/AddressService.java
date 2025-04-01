package com.service;

import com.entity.Address;

import java.util.List;

public interface AddressService {
    void saveAddress(List<Address> addresses);
    void updateAddress(Address address, int addressId);
    void deleteAddress(int addressId);
    Address getAddressById(int addressId);
}
