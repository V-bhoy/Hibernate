package com.util;

import com.entity.User;

import java.util.Scanner;

public class InputUtil {
    public static int menuOptions(Scanner sc) {
        System.out.println("Enter the menu option: ");
        System.out.println("1. Register a new user");
        System.out.println("2. Update an existing user");
        System.out.println("3. Update an existing customer user with detached statw");
        System.out.println("4. Delete an existing user");
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

    public static User acceptUserDetails(Scanner sc) {
        User u = new User();
        String phone = "";
        while (phone.isEmpty()) {
            System.out.println("Enter the phone of the customer, you cannot update later: ");
            phone = sc.nextLine();
        }
        try {
            u.setName("John Doe");
            u.setAge(24);
            u.setAddress("Pune");
            u.setPhone(phone);
            return u;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static User acceptUserDetailsToUpdate(Scanner sc) {
        User u = new User();
        String name = "";
        int age = 0;
        String address = "";
        while (name.isEmpty()) {
            System.out.println("Enter the name of the user: ");
            name = sc.nextLine();
        }
        while (true) {
            System.out.println("Enter the age of the user: ");
            if (sc.hasNextInt()) {
                age = sc.nextInt();
                sc.nextLine();
                if(age > 0) {
                    break;
                }
            }
            else{
                sc.next();
            }
        }
        while (address.isEmpty()) {
            System.out.println("Enter the address of the user: ");
            address = sc.nextLine();
        }
        try {
            u.setName(name);
            u.setAge(age);
            u.setAddress(address);
            return u;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    public static int acceptUserId(Scanner sc) {
        while(true){
            System.out.println("Enter the user ID: ");
            if (sc.hasNextInt()) {
                int userId = sc.nextInt();
                sc.nextLine();
                if (userId > 0) {
                    return userId;
                }
            } else {
                sc.next();
                System.out.println("Please enter a valid customer ID!");
            }
        }
    }

}