package com.service;

import com.entity.Customer;
import com.entity.Product;
import com.repository.CustomerRepo;

import java.util.List;

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
    public Object getCustomerName(int customerId) {
        return customerRepo.getCustomerName(customerId);
    }

    @Override
    public Customer getCustomerWithProductDetails(int customerId) {
        return customerRepo.getCustomerWithProductDetails(customerId);
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

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.getAllCustomers();
    }

    @Override
    public List<Customer> getAllCustomersWithProductDetails() {
        return customerRepo.getAllCustomersWithProductDetails();
    }

    @Override
    public List<Product> getAllProducts() {
        return customerRepo.getAllProducts();
    }

    @Override
    public List<Customer> getCustomersWithElectronicProducts() {
        return customerRepo.getCustomersWithElectronicProducts();
    }

    @Override
    public List<Customer> getCustomersWithProductPriceGreaterThanThousand() {
        return customerRepo.getCustomersWithProductPriceGreaterThanThousand();
    }

    @Override
    public List<Customer> getPaginatedCustomersWithProducts(int pageNo) {
        return customerRepo.getPaginatedCustomersWithProducts(pageNo);
    }

    @Override
    public int getCountOfProductType(char type) {
        return customerRepo.getCountOfProductType(type);
    }
}
