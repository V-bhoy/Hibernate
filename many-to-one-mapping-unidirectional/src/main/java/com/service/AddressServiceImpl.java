package com.service;

import com.entity.Address;
import com.repository.AddressRepo;

import java.util.List;

public class AddressServiceImpl implements AddressService{
    private AddressRepo addressRepo;

    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }
    @Override
    public void saveAddress(List<Address> addresses) {
        try{
            addressRepo.saveAddress(addresses);
        }catch (Exception e){
            throw new RuntimeException("Error occurred in address service handler: ",e);
        }
    }

    @Override
    public void updateAddress(Address address, int addressId) {
        try{
            addressRepo.updateAddress(address, addressId);
        }catch (Exception e){
            throw new RuntimeException("Error occurred in address service handler: ",e);
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        try{
           addressRepo.deleteAddress(addressId);
        }catch (Exception e){
            throw new RuntimeException("Error occurred in address service handler: ",e);
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        return addressRepo.getAddressById(addressId);
    }
}
