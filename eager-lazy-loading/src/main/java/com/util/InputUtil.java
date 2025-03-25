package com.util;

import java.util.Scanner;

public class InputUtil {
    public static int acceptId(Scanner sc) {
        System.out.println("Enter ID: ");
        int id;
        while(true){
            if(sc.hasNextInt()){
                id = sc.nextInt();
                sc.nextLine();
                if(id<=0){
                    System.out.println("Invalid ID! Enter again: ");
                    continue;
                }
                return id;
            }else {
                System.out.println("Invalid ID! Enter again: ");
            }
        }
    }
}
