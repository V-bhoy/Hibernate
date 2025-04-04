package com.service;

import com.entity.Customer;
import com.entity.Product;
import com.repository.CustomerRepo;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void insertCustomer(Customer customer) {
        try{
            customerRepo.insertCustomer(customer);
        }catch(Exception e){
            throw new RuntimeException("Error inserting customer", e);
        }
    }

    @Override
    public Customer getCustomer(int customerId) {
        return customerRepo.getCustomer(customerId);
    }

    @Override
    public void updateCustomer(Customer customer, int customerId) {
        try{
            customerRepo.updateCustomer(customer, customerId);
        }catch(Exception e){
            throw new RuntimeException("Error updating customer", e);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try{
            customerRepo.deleteCustomer(customerId);
        }catch(Exception e){
            throw new RuntimeException("Error deleting customer", e);
        }
    }

    @Override
    public void updateCustomerProduct(Product product, int customerId) {
        try{
            customerRepo.updateCustomerProduct(product, customerId);
        }catch(Exception e){
            throw new RuntimeException("Error updating product", e);
        }
    }
}
