// SINGLE TABLE INHERITANCE
// it is a design pattern to store multiple types of related entities in a single table
// differentiating them using a discriminator column
// All subclasses share the same database table
// The discriminator column defines the type of each row
// Some columns may only be relevant for specific subclasses,
// leading to NULL values for unrelated fields.

// Advantages
// 1. Since data is in single table, reduces join operations
// 2. indexes can be optimized
// 3. better read performance

// Disadvantages
// 1. Wasted storage due to existing null values for unrelated columns
// 2. Adding new subclasses requires adding new columns, leading to table bloating.
// 3. Constraints like NOT NULL may not work properly for specific subclasses.

// When to use?
// When subclasses share many common attributes.
// When read performance is more important than storage optimization.
// When the number of subclasses is limited and table bloating is not a frequent concern.

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
