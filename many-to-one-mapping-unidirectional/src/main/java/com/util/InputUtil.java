package com.util;

import com.entity.Address;
import com.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputUtil {
    public static int menuOptions(Scanner sc) {
        System.out.println("Enter the menu option: ");
        System.out.println("1. Register a new person");
        System.out.println("2. Update an existing person");
       // System.out.println("3. Delete an existing person");
        System.out.println("3. Register addresses");
        System.out.println("4. Update address");
        System.out.println("5. Delete address");
        System.out.println("6. List address details by address Id");
        System.out.println("7. List person details only by address Id");
        System.out.println("8. List all details using address Id");
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

    public static Person acceptPersonDetails(Scanner sc) {
        Person person = new Person();
        String name;
        System.out.println("Enter the name of the person: ");
        while (true) {
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty! Enter again: ");
            } else {
                break;
            }
        }
        String contact;
        System.out.println("Enter the contact of the person: ");
        while (true) {
            contact = sc.nextLine().trim();
            if (contact.isEmpty()) {
                System.out.println("Name cannot be empty! Enter again: ");
            } else if (contact.length() != 10) {
                System.out.println("Enter valid contact number: ");
            } else {
                break;
            }
        }
        try {
            person.setName(name);
            person.setContact(contact);
            return person;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptPersonDetails(sc);
        }
    }

    public static Person acceptPersonDetailsToUpdate(Scanner sc) {
        Person person = new Person();
        System.out.println("Enter the name of the person to update or press enter to skip: ");
        String name = sc.nextLine().trim();
        System.out.println("Enter the contact of the person to update or press enter to skip: ");
        String contact = sc.nextLine().trim();
        if (!contact.isEmpty() && contact.length() != 10) {
            System.out.println("Enter valid contact number: ");
            return acceptPersonDetailsToUpdate(sc);
        }
        try {
            person.setName(name.isEmpty() ? null : name);
            person.setContact(contact.isEmpty() ? null : contact);
            return person;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptPersonDetails(sc);
        }
    }

    public static List<Address> acceptAddresses(Scanner sc, Person person) {
        List<Address> addresses = new ArrayList<>();
        System.out.println("Enter the number of address to register: ");
        if (sc.hasNextInt()) {
            int number = sc.nextInt();
            sc.nextLine();
            if (number <= 0) {
                System.out.println("Please enter a valid number of addresses!");
                return acceptAddresses(sc, person);
            } else {
                try {
                    for (int i = 0; i < number; i++) {
                        Address address = new Address();
                        String street = "";
                        String city = "";
                        while (street.isEmpty()) {
                            System.out.println("Enter street: ");
                            street = sc.nextLine().trim();
                        }
                        while (city.isEmpty()) {
                            System.out.println("Enter city: ");
                            city = sc.nextLine().trim();
                        }
                        address.setStreet(street);
                        address.setCity(city);
                        address.setPerson(person);
                        addresses.add(address);
                    }
                    return addresses;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return acceptAddresses(sc, person);
                }
            }
        } else {
            sc.next();
            System.out.println("Please enter a valid number of addresses!");
            return acceptAddresses(sc, person);
        }
    }

    public static int acceptAddressId(Scanner sc) {
        System.out.println("Enter the address ID: ");
        if (sc.hasNextInt()) {
            int personId = sc.nextInt();
            sc.nextLine();
            if (personId <= 0) {
                System.out.println("Please enter a valid address ID!");
                return acceptAddressId(sc);
            }
            return personId;
        } else {
            sc.next();
            System.out.println("Please enter a valid address ID!");
            return acceptAddressId(sc);
        }
    }

    public static Address acceptAddressToUpdate(Scanner sc) {
        Address address = new Address();
        System.out.println("Enter the street to update or press enter to skip: ");
        String street = sc.nextLine().trim();
        System.out.println("Enter the city to update or press enter to skip: ");
        String city = sc.nextLine().trim();
        try {
            address.setStreet(street.isEmpty() ? null : street);
            address.setCity(city.isEmpty() ? null : city);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptAddressToUpdate(sc);
        }
        return address;
    }

    public static int acceptPersonId(Scanner sc) {
        System.out.println("Enter the person ID: ");
        if (sc.hasNextInt()) {
            int personId = sc.nextInt();
            sc.nextLine();
            if (personId <= 0) {
                System.out.println("Please enter a valid person ID!");
                return acceptPersonId(sc);
            }
            return personId;
        } else {
            sc.next();
            System.out.println("Please enter a valid person ID!");
            return acceptPersonId(sc);
        }
    }
}
