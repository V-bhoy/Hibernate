package com.util;

import com.entity.Clothing;
import com.entity.Customer;
import com.entity.Electronics;
import com.entity.Product;

import java.util.Scanner;

public class InputUtil {
    public static int menuOptions(Scanner sc) {
        System.out.println("Enter the menu option: ");
        System.out.println("1. Register a new customer");
        System.out.println("2. Update an existing customer");
        System.out.println("3. Update an existing customer product");
        System.out.println("4. Delete an existing customer");
        System.out.println("5. List customer details using customer Id");
        System.out.println("6. List customer details with product using customer Id");
        System.out.println("7. Fetch all customers");
        System.out.println("8. Fetch all customers with products");
        System.out.println("9. Fetch all customer products");
        System.out.println("SOME COMPLEX QUERIES: ");
        System.out.println("10. Get customers with electronic products");
        System.out.println("11. Get customers with price > 1000");
        System.out.println("12. Get count of product type");
        System.out.println("13. Get paginated customers with page number with size of 20");
        System.out.println("14. Get customer name using customer Id");
        if (sc.hasNextInt()) {
            int option = sc.nextInt();
            sc.nextLine();
            if (option <= 0) {
                System.out.println("Please enter a valid menu option! ");
                return menuOptions(sc);
            }
            return option;
        } else {
            sc.next();
            System.out.println("Please enter a valid menu option! ");
            return menuOptions(sc);
        }
    }

    public static boolean wantToContinue(Scanner sc) {
        System.out.println("Enter y to continue or n to skip: ");
        char input = sc.next().toUpperCase().charAt(0);
        return input == 'Y';
    }

    public static Customer acceptCustomerDetails(Scanner sc) {
        Customer c = new Customer();
        String name = "";
        while (name.isEmpty()) {
            System.out.println("Enter the name of the customer: ");
            name = sc.nextLine();
        }
        Product p = acceptProductDetails(sc);
        try {
            c.setName(name);
            c.setProduct(p);
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptCustomerDetails(sc);
        }
    }

    public static Customer acceptCustomerDetailsToUpdate(Scanner sc) {
        Customer c = new Customer();
        String name = "";
        while (name.isEmpty()) {
            System.out.println("Enter the name of the customer: ");
            name = sc.nextLine();
        }
        try {
            c.setName(name);
            return c;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptCustomerDetailsToUpdate(sc);
        }
    }

    public static Product acceptProductDetailsToUpdate(Scanner sc, Product p) {
        while (true) {
            try {
                System.out.println("Do you want to update the existing product details? (y/n)");
                char input = sc.next().toUpperCase().charAt(0);
                sc.nextLine();
                if (input == 'Y') {
                    if (p instanceof Electronics) {
                        int warrantyPeriod = 0;
                        while (warrantyPeriod <= 0 || warrantyPeriod > 10) {
                            System.out.println("Please enter a valid warranty period in years: ");
                            if (sc.hasNextInt()) {
                                warrantyPeriod = sc.nextInt();
                                sc.nextLine();
                            } else {
                                sc.next();
                            }
                        }
                        ((Electronics) p).setWarrantyPeriod(warrantyPeriod);
                    }
                    if (p instanceof Clothing) {
                        String size = "";
                        while (size.isEmpty()) {
                            System.out.println("Please enter a size of the clothing: ");
                            size = sc.nextLine();
                        }
                        ((Clothing) p).setSize(size);
                    }
                    return p;
                } else if(input == 'N') {
                    return acceptProductDetails(sc);
                }else{
                    System.out.println("Please enter 'Y' or 'N' to continue! ");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }

    public static int acceptCustomerId(Scanner sc) {
        System.out.println("Enter the customer ID: ");
        if (sc.hasNextInt()) {
            int customerId = sc.nextInt();
            sc.nextLine();
            if (customerId <= 0) {
                System.out.println("Please enter a valid customer ID!");
                return acceptCustomerId(sc);
            }
            return customerId;
        } else {
            sc.next();
            System.out.println("Please enter a valid customer ID!");
            return acceptCustomerId(sc);
        }
    }


    public static Product acceptProductDetails(Scanner sc) {
        Product p;
        System.out.println("Enter the type of product. Enter E for electronics and C for clothing: ");
        while (true) {
            char input = sc.next().toUpperCase().charAt(0);
            if (input == 'E') {
                p = new Electronics();
                sc.nextLine();
                break;
            } else if (input == 'C') {
                p = new Clothing();
                sc.nextLine();
                break;
            } else {
                sc.next();
                System.out.println("Please enter a valid product type!");
            }
        }
        String productName = "";
        int price = 0;
        while (productName.isEmpty()) {
            System.out.println("Enter the product name: ");
            productName = sc.nextLine();
        }
        while (price <= 0) {
            System.out.println("Please enter a valid product price: ");
            if (sc.hasNextInt()) {
                price = sc.nextInt();
                sc.nextLine();
            } else {
                sc.next();
            }
        }

        try {
            p.setProductName(productName);
            p.setPrice(price);
            if (p instanceof Electronics) {
                int warrantyPeriod = 0;
                while (warrantyPeriod <= 0 || warrantyPeriod > 10) {
                    System.out.println("Please enter a valid warranty period in years: ");
                    if (sc.hasNextInt()) {
                        warrantyPeriod = sc.nextInt();
                        sc.nextLine();
                    } else {
                        sc.next();
                    }
                }
                ((Electronics) p).setWarrantyPeriod(warrantyPeriod);
            }
            if (p instanceof Clothing) {
                String size = "";
                while (size.isEmpty()) {
                    System.out.println("Please enter a size of the clothing: ");
                    size = sc.nextLine();
                }
                ((Clothing) p).setSize(size);
            }
            return p;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptProductDetails(sc);
        }
    }

    public static char acceptProductType(Scanner sc) {
        while (true) {
            System.out.println("Enter the product type: ");
            char input = sc.next().toUpperCase().charAt(0);
            sc.nextLine();
            if(input == 'E' || input == 'C'){
                return input;
            }
            System.out.println("Please enter a valid product type!");
        }
    }

    public static int acceptPageNo(Scanner sc) {
        while(true){
            System.out.println("Enter the page number: ");
            if (sc.hasNextInt()) {
                int pageNo = sc.nextInt();
                sc.nextLine();
                if (pageNo > 0) {
                    return pageNo;
                }
                System.out.println("Please enter a valid page number!");
            }else{
                sc.next();
                System.out.println("Please enter a valid page number!");
            }
        }
    }
}