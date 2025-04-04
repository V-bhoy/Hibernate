// TABLE PER CLASS INHERITANCE
// each subclass in the inheritance hierarchy gets its own table
// each subclass table contains all the fields from the base class and its own fields
// No data is inserted in the base class table

// Advantages
// 1. No joins needed — everything is in one table per class, faster read operations
// 2. Each table maps directly to its entity, simple subclass queries

// Disadvantages
// 1. Each subclass table has columns for all inherited fields, data duplication
// 2. SELECT * FROM Product = UNION ALL from all subclass tables, complex polymorphic queries
// 3. Subclasses are fully independent, there is no integrity between subclasses

// When to use?
// You want to avoid base table altogether
// You want subclass tables fully independent
// You mostly query specific types (not polymorphic) that is data from subclass tables not altogether

// DO NOT USE IDENTITY FOR PRIMARY KEY
// Each subclass (Electronics, Clothing) has its own table and hence its own primary key column.
// If you use GenerationType.IDENTITY, the database will independently auto-generate IDs in each table.
// Now both tables might have rows with id = 1, which can cause:
//	•	Duplicate keys in result sets.
//	•	Confusion in UI or business logic when using IDs.
// A shared sequence can ensure unique IDs across all subclass tables.
// This avoids ID collisions during polymorphic queries.

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
