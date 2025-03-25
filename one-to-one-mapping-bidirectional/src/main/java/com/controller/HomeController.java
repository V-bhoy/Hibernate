package com.controller;

import com.repository.AdhaarRepoImpl;
import com.repository.PersonRepoImpl;
import com.service.AdhaarService;
import com.service.AdhaarServiceImpl;
import com.service.PersonService;
import com.service.PersonServiceImpl;
import com.entity.AdhaarCard;
import com.entity.Person;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.Scanner;

public class HomeController {
    public static void main(String[] args) {
        HomeController demo = new HomeController();
        demo.run();
    }

    public void run() {
        PersonService ps = new PersonServiceImpl(new PersonRepoImpl());
        AdhaarService as = new AdhaarServiceImpl(new AdhaarRepoImpl());
        System.out.println("WELCOME TO THE HOME PAGE!");
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1:
                        Person person = InputUtil.acceptPersonDetailsToSave(sc);
                        AdhaarCard adhaarCard = InputUtil.acceptAdhaarCardDetailsToSave(sc);
                        person.setAdhaarCard(adhaarCard);
                        ps.registerPersonDetails(person);
                        System.out.println("Person Details saved successfully!");
                        break;
                    case 2: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPersonDetailsById(personId);
                        if (p == null) {
                            System.out.println("Person not found!");
                        } else {
                            Person updatedPerson = InputUtil.acceptPersonDetailsToUpdate(sc);
                            AdhaarCard updatedAdhaarCard = InputUtil.acceptAdhaarCardDetailsToUpdate(sc);
                            updatedPerson.setAdhaarCard(updatedAdhaarCard);
                            ps.updatePersonDetails(updatedPerson, personId);
                            System.out.println("Person Details updated successfully!");
                        }
                    }
                    break;
                    case 3: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPersonDetailsById(personId);
                        if (p == null) {
                            System.out.println("Person not found!");
                        } else {
                            ps.deletePersonDetails(personId);
                            System.out.println("Person details deleted successfully!");
                        }
                    }
                    break;
                    case 4: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPersonDetailsById(personId);
                        if (p == null) {
                            System.out.println("Person not found!");
                        } else {
                            System.out.println("Person details requested successfully - ");
                            System.out.println(p);
                        }
                    }
                    break;
                    case 5: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPersonDetailsById(personId);
                        if (p == null) {
                            System.out.println("Person not found!");
                        } else {
                            AdhaarCard adhaar = p.getAdhaarCard();
                            System.out.println("Adhaar details requested successfully - ");
                            System.out.println(adhaar);
                        }
                    }
                    break;
                    case 6:
                    {
                        int adhaarId = InputUtil.acceptAdhaarId(sc);
                        AdhaarCard details = as.getDetailsByAdhaarId(adhaarId);
                        if (details == null) {
                            System.out.println("Adhaar not found!");
                        }else{
                            System.out.println("Details requested successfully - ");
                            System.out.println(details.toString(true));
                        }
                    }
                    break;
                    default:
                        System.out.println("Invalid choice");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
           e.printStackTrace();
        }
        System.out.println("THANK YOU!");
        HibernateUtil.shutDown();
    }
}
