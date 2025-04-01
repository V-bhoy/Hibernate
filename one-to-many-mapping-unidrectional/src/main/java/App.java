import com.entity.Address;
import com.entity.Person;
import com.repository.PersonRepoImpl;
import com.service.PersonService;
import com.service.PersonServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        System.out.println("WELCOME TO THE 1-N MAPPING UNIDIRECTIONAL DEMO!");
        PersonService ps = new PersonServiceImpl(new PersonRepoImpl());
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        Person p = InputUtil.acceptPersonDetails(sc);
                        ps.savePerson(p);
                        System.out.println("Person added successfully!");
                    }
                    break;
                    case 2: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            Person updatedPerson = InputUtil.acceptPersonDetailsToUpdate(sc);
                            ps.updatePerson(updatedPerson, personId);
                            System.out.println("Person updated successfully!");
                        } else {
                            System.out.println("Person not found!");
                        }
                    }
                    break;
                    case 3: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            ps.deletePerson(personId);
                            System.out.println("Person deleted successfully!");
                        } else {
                            System.out.println("Person not found!");
                        }
                    }
                    break;
                    case 4: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            List<Address> addresses = InputUtil.acceptAddresses(sc);
                            if (addresses.isEmpty()) {
                                System.out.println("No addresses found!");
                            } else {
                                ps.registerAddress(addresses, personId);
                                System.out.println("Addresses added successfully!");
                            }
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    case 5: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            List<Address> addresses = p.getAddresses();
                            if (addresses.isEmpty()) {
                                System.out.println("No addresses found!");
                            } else {
                                int addressId = InputUtil.acceptAddressId(sc, addresses);
                                if (addressId == 0) {
                                    System.out.println("No address found to update!");
                                    break;
                                }
                                Address updateAddress = InputUtil.acceptAddressToUpdate(sc);
                                ps.updateAddress(updateAddress, personId, addressId);
                                System.out.println("Addresses updated successfully!");
                            }
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    case 6: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            List<Address> addresses = p.getAddresses();
                            if (addresses.isEmpty()) {
                                System.out.println("No addresses found!");
                            } else {
                                List<Address> deleteAddresses = InputUtil.acceptAddressToDelete(sc, addresses);
                                if (deleteAddresses.isEmpty()) {
                                    System.out.println("No addresses found to delete!");
                                } else {
                                    ps.removeAddresses(deleteAddresses, personId);
                                    System.out.println("Addresses removed successfully!");
                                }
                            }
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    case 7: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            System.out.println(p.getAddresses());
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    case 8: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            System.out.println(p);
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    case 9: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person p = ps.getPerson(personId);
                        if (p != null) {
                            System.out.println(p.toString(true));
                        } else {
                            System.out.println("No person found!");
                        }
                    }
                    break;
                    default:
                        System.out.println("INVALID CHOICE!");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        HibernateUtil.shutdown();
        System.out.println("THANK YOU!");
    }
}
