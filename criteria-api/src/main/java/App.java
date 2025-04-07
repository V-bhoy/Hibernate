// Criteria API (Hibernate 5+)
// instead of writing hql or sql strings, we can use criteria api
// for creating type safe object-oriented dynamic queries
// which make refactoring easier and avoids syntax errors

// Adv
// - Type-safe
// - easier to refactor
// - Dynamic query building is easier

// Disadvantages
// - Verbose compared to HQL
// - Slight learning curve for complex joins and predicates

// When to use?
// When you need dynamic query generation.
// If you want type safety (compile-time checking).
// When query complexity grows and maintaining HQL becomes hard.

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
                    case 14: {
                        int customerId = InputUtil.acceptCustomerId(sc);
                        Object c = cs.getCustomerName(customerId);
                        if(c == null){
                            System.out.println("Customer not found!");
                        }else{
                            System.out.println("name: "+ c);
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

