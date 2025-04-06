package com.service;

import com.entity.Customer;
import com.entity.Product;

import java.util.List;

public interface CustomerService {
    void insertCustomer(Customer customer);
    Customer getCustomer(int customerId);
    Customer getCustomerWithProductDetails(int customerId);
    void updateCustomer(Customer customer, int customerId);
    void deleteCustomer(int customerId);
    void updateCustomerProduct(Product product, int customerId);
    List<Customer> getAllCustomers();
    List<Customer> getAllCustomersWithProductDetails();
    List<Product> getAllProducts();

    List<Customer> getCustomersWithElectronicProducts();
    List<Customer> getCustomersWithProductPriceGreaterThanThousand();
    List<Customer> getPaginatedCustomersWithProducts(int pageNo);
    int getCountOfProductType(char type);
}
