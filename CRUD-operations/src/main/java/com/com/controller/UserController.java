package com.com.controller;

import com.com.service.UserService;
import com.com.service.UserServiceImpl;
import com.entity.User;
import com.repository.UserRepoImpl;
import com.util.InputValidators;

import java.util.Scanner;

public class UserController {
    UserService us = new UserServiceImpl(new UserRepoImpl());
    Scanner sc = new Scanner(System.in);

    public void insertUser() {
        User user = new User();
        System.out.println("\nEnter your name: ");
        String name = sc.nextLine().trim();
        if(name.isEmpty()){
            System.out.println("\nName cannot be empty!\n");
            return;
        }
        user.setName(name);
        System.out.println("\nEnter your address: ");
        String address = sc.nextLine().trim();
        if(address.isEmpty()){
            System.out.println("\nAddress cannot be empty!\n");
            return;
        }
        user.setAddress(address);
        System.out.println("\nEnter your email: ");
        String email = sc.nextLine().trim();
        if(email.isEmpty()){
            System.out.println("\nEmail cannot be empty!\n");
            return;
        }
        if(!InputValidators.EmailValidator(email)){
            System.out.println("\nEmail is not a valid email address!\n");
            return;
        }
        user.setEmail(email);
        System.out.println("\nEnter your contact: ");
        String contact = sc.nextLine().trim();
        if(contact.isEmpty()){
            System.out.println("\nContact cannot be empty!\n");
            return;
        }
        if(!InputValidators.ContactValidator(contact)){
            System.out.println("\nContact is not valid!\n");
            return;
        }
        user.setContact(contact);
        System.out.println("\nEnter your username: ");
        String username = sc.nextLine().trim();
        if(username.isEmpty()){
            System.out.println("\nUsername cannot be empty!\n");
            return;
        }
        if(!InputValidators.UsernameValidator(username)){
            System.out.println("\nUsername is not valid! Min 4 and max 6 length with no special characters required!\n");
            return;
        }
        user.setUsername(username);

        System.out.println("\nEnter your password: ");
        String password = sc.nextLine().trim();
        if(password.isEmpty()){
            System.out.println("\nPassword cannot be empty!\n");
            return;
        }
        if(!InputValidators.PasswordValidator(password)){
            System.out.println("\nPassword is not valid!\n");
            System.out.println("Must contain  At least one uppercase letter, one lowercase letter,\n one digit, one special character (@#$%^&+=!) with 8 to 20 characters long.\n");
            return;
        }
        user.setPassword(password);
        System.out.println("\n"+us.insertUser(user)+"\n");
    }

    public void getUser() {
        System.out.println("\nEnter your userId: ");
        if(sc.hasNextInt()){
            int userId = sc.nextInt();
            sc.nextLine();
            System.out.println("\n"+us.getUser(userId)+"\n");
        }else{
            sc.next();
            System.out.println("\nEnter valid userId! ");
        }
    }

    public void updateUser() {
        User user = new User();
        System.out.println("\nEnter your userId: ");
        if(sc.hasNextInt()){
            int userId = sc.nextInt();
            sc.nextLine();
            if(userId <= 0 ){
                System.out.println("\nEnter valid userId! ");
                return;
            }
            user.setId(userId);
        }else{
            sc.next();
            System.out.println("\nEnter valid userId! ");
            return;
        }
        System.out.println("\nEnter your name or press enter to skip: ");
        String name = sc.nextLine().trim();
        if(!name.isEmpty()){
            user.setName(name);
        }
        System.out.println("\nEnter your address or press enter to skip: ");
        String address = sc.nextLine().trim();
        if(!address.isEmpty()){
            user.setAddress(address);
        }
        System.out.println("\nEnter your email or press enter to skip: ");
        String email = sc.nextLine().trim();
        if(!email.isEmpty() && !InputValidators.EmailValidator(email)){
            System.out.println("\nEmail is not a valid email address!\n");
            return;
        }
        else if(!email.isEmpty()){
            user.setEmail(email);
        }
        System.out.println("\nEnter your contact or press enter to skip: ");
        String contact = sc.nextLine().trim();
        if(!contact.isEmpty() && !InputValidators.ContactValidator(contact)){
            System.out.println("\nContact is not a valid!\n");
            return;
        }
        else if(!contact.isEmpty()){
            user.setContact(contact);
        }
        // We can have separate methods to update username and password
        System.out.println("\n"+us.updateUser(user)+"\n");
    }

    public void deleteUser() {
        System.out.println("\nEnter your userId: ");
        if(sc.hasNextInt()){
            int userId = sc.nextInt();
            sc.nextLine();
            if(userId <= 0 ){
                System.out.println("\nEnter valid userId! ");
                return;
            }
            System.out.println("\n"+us.deleteUser(userId)+"\n");
        }else{
            sc.next();
            System.out.println("\nEnter valid userId! ");
        }
    }
}
