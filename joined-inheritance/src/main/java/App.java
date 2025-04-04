// SINGLE TABLE INHERITANCE
// it is one of the three design pattern supported by JPA for mapping class hierarchies to database tables
// creates separate table for each related class and joins them to the super class using foreign key

// Advantages
// 1. No extra columns and unnecessary null values in parent table, clean DB design
// 2. Each subclass can have its own unique fields
// 3. Foreign keys ensure consistency between parent and child tables.

// Disadvantages
// 1. Every subclass query results in JOIN with the parent table resulting in slower queries.
// 2. You insert into the base table and the subclass table resulting in complex insert queries.
// 3. Updating type (e.g., Clothing → Electronics) requires deleting and recreating rows.

// When to use?
// You want clean separation of subclass fields in different tables.
// You’re okay with slightly slower read performance due to joins.
// You want a normalized database schema.

import com.entity.Customer;
import com.entity.Product;
import com.repository.CustomerRepoImpl;
import com.service.CustomerService;
import com.service.CustomerServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run(){
        System.out.println("WELCOME TO SINGLE TABLE INHERITANCE DEMO!");
        CustomerService cs = new CustomerServiceImpl(new CustomerRepoImpl());
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        Customer c = InputUtil.acceptCustomerDetails(sc);
                        cs.insertCustomer(c);
                        System.out.println("Customer added successfully!");
                    }
                    break;
                    case 2: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Customer c = cs.getCustomer(customerId);
                        if (c != null) {
                            Customer updatedCustomer = InputUtil.acceptCustomerDetailsToUpdate(sc);
                            cs.updateCustomer(updatedCustomer, customerId);
                            System.out.println("Customer updated successfully!");
                        } else {
                            System.out.println("Customer not found!");
                        }
                    }
                    break;
                    case 3: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Customer c = cs.getCustomer(customerId);
                        if (c != null) {
                            Product p = c.getProduct();
                            Product updatedProduct = InputUtil.acceptProductDetailsToUpdate(sc, p);
                            cs.updateCustomerProduct(updatedProduct, customerId);
                            System.out.println("Product updated successfully!");
                        } else {
                            System.out.println("Customer not found!");
                        }
                    }
                    break;
                    case 4: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Customer c = cs.getCustomer(customerId);
                        if (c != null) {
                            cs.deleteCustomer(customerId);
                            System.out.println("Customer deleted successfully!");
                        } else {
                            System.out.println("Customer not found!");
                        }
                    }
                    break;
                    case 5: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Customer c = cs.getCustomer(customerId);
                        if (c != null) {
                            System.out.println(c);
                        } else {
                            System.out.println("Customer not found!");
                        }
                    }
                    break;
                    default:
                        System.out.println("INVALID CHOICE!");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        HibernateUtil.shutdown();
        System.out.println("THANK YOU!");
    }
}
