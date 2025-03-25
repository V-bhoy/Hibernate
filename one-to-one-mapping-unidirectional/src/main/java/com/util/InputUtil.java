package com.util;

import com.entity.AdhaarCard;
import com.entity.Person;
import com.enums.Gender;

import java.util.Scanner;

public class InputUtil {
    private InputUtil() {

    }

    public static int menuOptions(Scanner sc){
        System.out.println("\nPLEASE CHOOSE NUMBER FROM 1 TO 5: ");
        System.out.println("1. Register Person Details");
        System.out.println("2. Update Person Details");
        System.out.println("3. Delete Person Details");
        System.out.println("4. Fetch Person Details");
        System.out.println("5. Fetch Adhaar Details");
        if(sc.hasNextInt()) {
            int option = sc.nextInt();
            sc.nextLine();
            if(option <= 0){
                System.out.println("Invalid option!");
                return menuOptions(sc);
            }else{
                return option;
            }
        }
        else {
            System.out.println("Invalid option!");
            sc.next();
            return menuOptions(sc);
        }
    }

    public static boolean wantToContinue(Scanner sc){
        System.out.println("\nPress  Y to continue and N to exit: ");
        char choice = sc.nextLine().toUpperCase().charAt(0);
        return choice == 'Y';
    }

    public static Person acceptPersonDetailsToSave(Scanner sc){
        System.out.println("Enter your name: ");
        String name;
        do{
            name = sc.nextLine().trim();
            if(name.isEmpty()){
                System.out.println("Name cannot be empty!");
            }
        }while (name.isEmpty());
        System.out.println("Enter your address: ");
        String address;
        do{
            address = sc.nextLine().trim();
            if(address.isEmpty()){
                System.out.println("Address cannot be empty!");
            }
        }while (address.isEmpty());
        System.out.println("Enter your phone number: ");
        String phone;
        do{
            phone = sc.nextLine().trim();
            if(phone.isEmpty()){
                System.out.println("Phone number cannot be empty!");
            }
            if(!phone.isEmpty() && !Validators.ContactValidator(phone)){
                System.out.println("Enter valid phone number!");
            }
        }while (phone.isEmpty() || !Validators.ContactValidator(phone));
        System.out.println("Enter your age: ");
        int age;
        do{
            if(sc.hasNextInt()){
                age = sc.nextInt();
                if(age<=0){
                    System.out.println("Enter valid age!");
                }else{
                    sc.nextLine();
                    break;
                }
            }else{
                System.out.println("Invalid age!");
                sc.next();
            }
        }while (true);
        System.out.println("Enter your gender (MALE / FEMALE / OTHER): ");
        Gender gender = null;
        while (gender == null) {
            try {
                String genderInput = sc.nextLine().trim().toUpperCase();
                if (genderInput.isEmpty()) {
                    System.out.println("Gender cannot be empty! Please enter MALE, FEMALE, or OTHER.");
                    continue;
                }
                gender = Gender.valueOf(genderInput); // Convert input to Enum
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input! Please enter MALE, FEMALE, or OTHER.");
            }
        };
        try {
            Person person = new Person();
            person.setName(name);
            person.setAddress(address);
            person.setPhone(phone);
            person.setAge(age);
            person.setGender(gender);
            return person;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return acceptPersonDetailsToSave(sc);
        }
    }

    public static Person acceptPersonDetailsToUpdate(Scanner sc){
        System.out.println("Enter your name or press enter to skip: ");
        String name = sc.nextLine().trim();
        System.out.println("Enter your address or press enter to skip: ");
        String address = sc.nextLine().trim();
        System.out.println("Enter your phone number or press enter to skip: ");
        String phone = null;
        while (true) {
            String phoneInput = sc.nextLine().trim();
            if (phoneInput.isEmpty()) {
                phone = null;
                break; // Allow skipping the phone number
            }
            if (Validators.ContactValidator(phoneInput)) {
                phone = phoneInput; // Valid phone number
                break;
            } else {
                System.out.println("Invalid phone number! Please enter a valid number.");
            }
        }
        int age = 0;
        while (true) {
            System.out.println("Enter your age or press 0 to skip: ");
            if (sc.hasNextInt()) {
                age = sc.nextInt();
                sc.nextLine(); // Consume newline
                if (age >= 0) {
                    break;
                }
            } else {
                System.out.println("Invalid age! Please enter a number.");
                sc.next(); // Consume invalid input
            }
        }
        Gender gender = null;
        while (true) {
            System.out.println("Enter your gender (MALE / FEMALE / OTHER) or press enter to skip: ");
            String genderInput = sc.nextLine().trim().toUpperCase();
            if (genderInput.isEmpty()) {
                break; // Allow skipping gender
            }
            try {
                gender = Gender.valueOf(genderInput);
                break; // Exit loop if valid input
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender! Please enter MALE, FEMALE, or OTHER.");
            }
        }
        try {
            Person person = new Person();
            person.setName(!name.isEmpty() ? name : null);
            person.setAddress(!address.isEmpty() ? address : null);
            person.setPhone(phone);
            if (age > 0) person.setAge(age);
            if (gender != null) person.setGender(gender);
            return person;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return acceptPersonDetailsToUpdate(sc);
        }
    }

    public static AdhaarCard acceptAdhaarCardDetailsToSave(Scanner sc){
        System.out.println("Enter your adhaar number: ");
        String adhaarNumber;
        do{
            adhaarNumber = sc.nextLine().trim();
            if(adhaarNumber.isEmpty()){
                System.out.println("Adhaar number cannot be empty!");
            }
            if (!adhaarNumber.isEmpty() && adhaarNumber.length() !=12){
                System.out.println("Enter valid adhaar number!");
            }
        }while (adhaarNumber.length() != 12);
        try{
            AdhaarCard adhaarCard = new AdhaarCard();
            adhaarCard.setAdhaarNo(adhaarNumber);
            return adhaarCard;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return acceptAdhaarCardDetailsToSave(sc);
        }
    }

    public static AdhaarCard acceptAdhaarCardDetailsToUpdate(Scanner sc){
        System.out.println("Enter your adhaar number or press enter to skip: ");
        String adhaarNumber = sc.nextLine().trim();
        try{
            AdhaarCard adhaarCard = new AdhaarCard();
            if(!adhaarNumber.isEmpty()){
                adhaarCard.setAdhaarNo(adhaarNumber);
            }else{
                adhaarCard.setAdhaarNo(null);
            }
            return adhaarCard;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return acceptAdhaarCardDetailsToUpdate(sc);
        }
    }

    public static int acceptPersonId(Scanner sc){
        int personId;
        while (true){
            System.out.println("Enter your person Id: ");
            if(sc.hasNextInt()){
                personId = sc.nextInt();
                sc.nextLine();
                if(personId <= 0){
                    System.out.println("Person Id is not valid!");
                }else{
                    break;
                }
            }else{
                sc.next();
                System.out.println("Invalid person Id!");
            }
        }
        return personId;
    }

}
