// HQL query
// it is object-oriented query language specific to hibernate
// operates on entities and variables
// handles inheritance using polymorphism
// slower due to hibernate translation

// Adv
// - good for bulk operations
// - readable and cleaner than sql
// - supports joins, aggregates, subqueries on entities
// - database agnostic
// - object-oriented querying

// Disadvantages
// - No Entity Lifecycle Events
// - No Dirty Checking / Cascade
// - Error-Prone for Complex Objects
// - Can Be Verbose for Simple Queries
// - Limited IDE Autocompletion

// When to use?
// use hql for ready heavy complex queries and batch updates/deletes
// complex joins with conditions & polymorphic queries
// avoid hql if you need cascading, lifecycle events and persistence context

import com.entity.Customer;
import com.entity.Product;
import com.repository.CustomerRepoImpl;
import com.service.CustomerService;
import com.service.CustomerServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.List;
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
                        Customer c = cs.getCustomerWithProductDetails(customerId);
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
                    case 6: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Customer c = cs.getCustomerWithProductDetails(customerId);
                        if (c != null) {
                            System.out.println("Customer with product details: ");
                            System.out.println(c);
                        } else {
                            System.out.println("Customer not found!");
                        }
                    }
                    break;
                    case 7: {
                       List<Customer> cList = cs.getAllCustomers();
                       for (Customer c : cList) {
                           System.out.println(c);
                       }
                    }
                    break;
                    case 8: {
                        List<Customer> cList = cs.getAllCustomersWithProductDetails();
                        for (Customer c : cList) {
                            System.out.println(c);
                        }
                    }
                    break;
                    case 9: {
                        List<Product> pList = cs.getAllProducts();
                        for (Product p : pList) {
                            System.out.println(p.toString(true));
                        }
                    }
                    break;
                    case 10: {
                        List<Customer> cList = cs.getCustomersWithElectronicProducts();
                        if(cList.isEmpty()){
                            System.out.println("No customers found!");
                        }else{
                            for (Customer c : cList) {
                                System.out.println(c);
                            }
                        }
                    }
                    break;
                    case 11: {
                        List<Customer> cList = cs.getCustomersWithProductPriceGreaterThanThousand();
                        if(cList.isEmpty()){
                            System.out.println("No customers found!");
                        }else{
                            for (Customer c : cList) {
                                System.out.println(c);
                            }
                        }
                    }
                    break;
                    case 12: {
                        char type = InputUtil.acceptProductType(sc);
                        int count = cs.getCountOfProductType(type);
                        System.out.println(count);
                    }
                    break;
                    case 13: {
                        int pageNo = InputUtil.acceptPageNo(sc);
                        List<Customer> cList = cs.getPaginatedCustomersWithProducts(pageNo);
                        if(cList.isEmpty()){
                            System.out.println("No customers found!");
                        }else{
                            for (Customer c : cList) {
                                System.out.println(c);
                            }
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
