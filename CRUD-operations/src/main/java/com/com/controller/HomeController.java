package com.com.controller;

import java.util.Scanner;

public class HomeController {
    public static void main(String[] args) {
        HomeController demo = new HomeController();
        demo.run();
    }

    public void run(){
        System.out.println("\nWELCOME TO THE USER DATABASE!\n");
        UserController uc = new UserController();
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
           System.out.println("\nMake a choice between 1 to 5\n");
           System.out.println("1. Insert User");
           System.out.println("2. Get User");
           System.out.println("3. Update User");
           System.out.println("4. Delete User");
           System.out.println("5. Exit\n");
           if(sc.hasNextInt()){
               choice = sc.nextInt();
               sc.nextLine();
               switch (choice) {
                   case 1:
                       uc.insertUser();
                       break;
                   case 2:
                       uc.getUser();
                       break;
                   case 3:
                       uc.updateUser();
                       break;
                   case 4:
                       uc.deleteUser();
                       break;
                   case 5:
                       System.out.println("\nTHANK YOU FOR USING THIS PROGRAM!\n");
                       break;
                   default:
                       System.out.println("\nEnter a valid choice!\n");

               }
           }
        }while (choice != 5);
    }
}
