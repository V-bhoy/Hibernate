package com.service;

import com.entity.Customer;
import com.entity.Product;

public interface CustomerService {
    void insertCustomer(Customer customer);
    Customer getCustomer(int customerId);
    void updateCustomer(Customer customer, int customerId);
    void deleteCustomer(int customerId);
    void updateCustomerProduct(Product product, int customerId);
}
