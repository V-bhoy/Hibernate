package com.util;

import com.entity.Course;
import com.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputUtil {
    public static int menuOptions(Scanner sc) {
        System.out.println("Enter the menu option: ");
        System.out.println("1. Register a new student");
        System.out.println("2. Update an existing student");
        System.out.println("3. Delete an existing student");
        System.out.println("4. Register courses");
        System.out.println("5. Update course");
        System.out.println("6. Remove courses");
        System.out.println("7. List all courses using student Id");
        System.out.println("8. List student details only using student Id");
        System.out.println("9. List all details using student Id");
        System.out.println("10. List course using course Id");
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

    public static Student acceptStudentDetails(Scanner sc){
        Student s = new Student();
        String name = "";
        while(name.isEmpty()) {
            System.out.println("Enter the name of the student: ");
            name = sc.nextLine();
        }
        try{
            s.setName(name);
            return s;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return acceptStudentDetails(sc);
        }
    }

    public static int acceptStudentId(Scanner sc){
        System.out.println("Enter the student ID: ");
        if(sc.hasNextInt()) {
            int studentId = sc.nextInt();
            sc.nextLine();
            if(studentId <= 0) {
                System.out.println("Please enter a valid student ID!");
                return acceptStudentId(sc);
            }
            return studentId;
        }else{
            sc.next();
            System.out.println("Please enter a valid student ID!");
            return acceptStudentId(sc);
        }
    }

    public static int acceptCourseId(Scanner sc){
        System.out.println("Enter the course ID: ");
        if(sc.hasNextInt()) {
            int courseId = sc.nextInt();
            sc.nextLine();
            if(courseId <= 0) {
                System.out.println("Please enter a valid course ID!");
                return acceptCourseId(sc);
            }
            return courseId;
        }else{
            sc.next();
            System.out.println("Please enter a valid course ID!");
            return acceptCourseId(sc);
        }
    }

    public static List<Course> acceptCoursesToRegister(Scanner sc){
        System.out.println("Enter the number of courses to register:");
        if(sc.hasNextInt()) {
            int n = sc.nextInt();
            sc.nextLine();
            if(n < 0) {
                System.out.println("Please enter a valid number of courses!");
                return acceptCoursesToRegister(sc);
            }
            else{
                List<Course> courses = new ArrayList<Course>();
                for(int i = 0; i < n; i++) {
                    boolean addExistingCourse = false;
                    System.out.println("Do you want to add an existing course? (y/n)");
                    char input = sc.next().toUpperCase().charAt(0);
                    sc.nextLine();
                    if(input == 'Y') {
                        addExistingCourse = true;
                    }
                    if(addExistingCourse) {
                        while(true) {
                            System.out.println("Please enter a valid course ID: ");
                            if(sc.hasNextInt()) {
                                int courseId = sc.nextInt();
                                sc.nextLine();
                                if(courseId <= 0) {
                                    System.out.println("Please enter a valid course ID!");
                                    continue;
                                }
                                try{
                                    Course course = new Course();
                                    course.setId(courseId);
                                    courses.add(course);
                                    break;
                                }catch(Exception e){
                                    System.out.println(e.getMessage());
                                    return acceptCoursesToRegister(sc);
                                }
                            }
                            else{
                                sc.next();
                            }
                        }
                    }else{
                        String title = "";
                        while(title.isEmpty()) {
                            System.out.println("Enter the course title: ");
                            title = sc.nextLine();
                        }
                        try{
                            Course c = new Course();
                            c.setTitle(title);
                            courses.add(c);
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                            return acceptCoursesToRegister(sc);
                        }
                    }
                }
                return courses;
            }
        }else{
            sc.next();
            System.out.println("Please enter a valid number of courses!");
            return acceptCoursesToRegister(sc);
        }
    }

    public static List<Course> acceptCoursesToDelete(Scanner sc, List<Course> courses){
        List<Course> coursesToDelete = new ArrayList<>();
        for(Course c : courses) {
            System.out.println(c);
            System.out.println("Enter y to delete or n to skip: ");
            char ch = sc.next().toUpperCase().charAt(0);
            sc.nextLine();
            if(ch == 'Y') {
                coursesToDelete.add(c);
            }
        }
        if(coursesToDelete.size() == courses.size()) {
            System.out.println("Are you sure you want to remove all courses? ");
            System.out.println("Enter y to continue or n to skip: ");
            char ch = sc.next().toUpperCase().charAt(0);
            sc.nextLine();
            if(ch == 'N') {
                return new ArrayList<>();
            }
        }
        return coursesToDelete;
    }

    public static Course acceptCourseDetails(Scanner sc){
        String title = "";
        while(title.isEmpty()) {
            System.out.println("Enter the course title: ");
            title = sc.nextLine();
        }
        try{
            Course c = new Course();
            c.setTitle(title);
            return c;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return acceptCourseDetails(sc);
        }
    }
}
