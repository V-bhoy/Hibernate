package com.util;

import com.entity.Address;
import com.entity.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("CallToPrintStackTrace")
public class InputUtil {
    public static int menuOptions(Scanner sc) {
        System.out.println("Enter the menu option: ");
        System.out.println("1. Register a new person");
        System.out.println("2. Update an existing person");
        System.out.println("3. Delete an existing person");
        System.out.println("4. Register addresses");
        System.out.println("5. Update address");
        System.out.println("6. Remove addresses");
        System.out.println("7. List all addresses");
        System.out.println("8. List person details only");
        System.out.println("9. List all details using person Id");
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

    public static List<Address> acceptAddresses(Scanner sc) {
        List<Address> addresses = new ArrayList<>();
        System.out.println("Enter the number of address to register: ");
        if (sc.hasNextInt()) {
            int number = sc.nextInt();
            sc.nextLine();
            if (number <= 0) {
                System.out.println("Please enter a valid number of addresses!");
                return acceptAddresses(sc);
            } else {
                try {
                    for (int i = 0; i < number; i++) {
                        Address address = new Address();
                        String street = "";
                        String city = "";
                        String state = "";
                        while (street.isEmpty()) {
                            System.out.println("Enter street: ");
                            street = sc.nextLine().trim();
                        }
                        while (city.isEmpty()) {
                            System.out.println("Enter city: ");
                            city = sc.nextLine().trim();
                        }
                        while (state.isEmpty()) {
                            System.out.println("Enter state: ");
                            state = sc.nextLine().trim();
                        }
                        address.setStreet(street);
                        address.setCity(city);
                        address.setState(state);
                        addresses.add(address);
                    }
                    return addresses;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return acceptAddresses(sc);
                }
            }
        } else {
            sc.next();
            System.out.println("Please enter a valid number of addresses!");
            return acceptAddresses(sc);
        }
    }

    public static int acceptAddressId(Scanner sc, List<Address> addresses) {
        for (Address address : addresses) {
            System.out.println(address);
            System.out.println("Enter y to update or n to continue: ");
            char ch = sc.nextLine().trim().toUpperCase().charAt(0);
            if (ch == 'Y') {
                return address.getId();
            }
        }
        return 0;
    }

    public static Address acceptAddressToUpdate(Scanner sc) {
        Address address = new Address();
        System.out.println("Enter the street to update or press enter to skip: ");
        String street = sc.nextLine().trim();
        System.out.println("Enter the city to update or press enter to skip: ");
        String city = sc.nextLine().trim();
        System.out.println("Enter the state to update or press enter to skip: ");
        String state = sc.nextLine().trim();
        try {
            address.setStreet(street.isEmpty() ? null : street);
            address.setCity(city.isEmpty() ? null : city);
            address.setState(state.isEmpty() ? null : state);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return acceptAddressToUpdate(sc);
        }
        return address;
    }

    public static List<Address> acceptAddressToDelete(Scanner sc, List<Address> addresses) {
        List<Address> addressesToDelete = new ArrayList<>();
        for (Address address : addresses) {
            System.out.println(address);
            System.out.println("Enter y to delete or n to skip: ");
            char ch = sc.nextLine().trim().toUpperCase().charAt(0);
            if (ch == 'Y') {
                addressesToDelete.add(address);
            }
        }
        if (addressesToDelete.size() == addresses.size()) {
            System.out.println("Are you sure you want to delete all addresses, enter y to continue or n to skip: ");
            char ch = sc.nextLine().trim().toUpperCase().charAt(0);
            if (ch == 'N') {
                return new ArrayList<>();
            }
        }
        return addressesToDelete;
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
