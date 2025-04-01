import com.entity.Address;
import com.entity.Person;
import com.repository.AddressRepoImpl;
import com.repository.PersonRepoImpl;
import com.service.AddressService;
import com.service.AddressServiceImpl;
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
        System.out.println("WELCOME TO THE MANY TO ONE MAPPING UNIDIRECTIONAL DEMO: ");
        PersonService ps = new PersonServiceImpl(new PersonRepoImpl());
        AddressService as = new AddressServiceImpl(new AddressRepoImpl());
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        Person person = InputUtil.acceptPersonDetails(sc);
                        ps.savePerson(person);
                        System.out.println("Person added successfully!");
                    }
                    break;
                    case 2: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person person = ps.getPersonById(personId);
                        if (person != null) {
                            Person newPerson = InputUtil.acceptPersonDetailsToUpdate(sc);
                            ps.updatePerson(newPerson, personId);
                        } else {
                            System.out.println("Person not found!");
                        }
                    }
                    break;
                    case 3: {
                        int personId = InputUtil.acceptPersonId(sc);
                        Person person = ps.getPersonById(personId);
                        if (person != null) {
                            List<Address> addresses = InputUtil.acceptAddresses(sc, person);
                            as.saveAddress(addresses);
                            System.out.println("Addresses added successfully!");
                        } else {
                            System.out.println("Person not found!");
                        }
                    }
                    break;
                    case 4: {
                        int addressId = InputUtil.acceptAddressId(sc);
                        Address address = as.getAddressById(addressId);
                        if (address != null) {
                            Address updateAddress = InputUtil.acceptAddressToUpdate(sc);
                            as.updateAddress(updateAddress, addressId);
                            System.out.println("Address updated successfully!");
                        } else {
                            System.out.println("Address not found!");
                        }
                    }
                    break;
                    case 5: {
                        int addressId = InputUtil.acceptAddressId(sc);
                        Address address = as.getAddressById(addressId);
                        if (address != null) {
                            as.deleteAddress(addressId);
                            System.out.println("Address deleted successfully!");
                        } else {
                            System.out.println("Address not found!");
                        }
                    }
                    break;
                    case 6: {
                        int addressId = InputUtil.acceptAddressId(sc);
                        Address address = as.getAddressById(addressId);
                        if (address != null) {
                            System.out.println(address);
                        } else {
                            System.out.println("Address not found!");
                        }
                    }
                    break;
                    case 7: {
                        int addressId = InputUtil.acceptAddressId(sc);
                        Address address = as.getAddressById(addressId);
                        if (address != null) {
                            System.out.println(address.getPerson());
                        } else {
                            System.out.println("Address not found!");
                        }
                    }
                    break;
                    case 8: {
                        int addressId = InputUtil.acceptAddressId(sc);
                        Address address = as.getAddressById(addressId);
                        if (address != null) {
                            System.out.println(address.toString(true));
                        } else {
                            System.out.println("Address not found!");
                        }
                    }
                    break;
                    default:
                        System.out.println("Invalid choice!");
                        break;
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
