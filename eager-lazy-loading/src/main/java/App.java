import com.entity.Person;
import com.entity.Vehicle;
import com.repository.OwnerRepoImpl;
import com.repository.VehicleRepoImpl;
import com.service.OwnerService;
import com.service.OwnerServiceImpl;
import com.service.VehicleService;
import com.service.VehicleServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.Scanner;

/*
When dealing with mappings, hibernate controls how the data is fetched from the database
This is controlled using EAGER and LAZY loading

If you look carefully, the owner entity is set to fetch lazy
while the vehicle entity is set to fetch eager
For 1-1 & Many-1 mappings, the fetch is eager by default
For 1-Many & Many-Many, the fetch is lazy by default

LAZY loading - best for performance, it loads the entity only when needed!
The related entity will not be loaded immediately
Hibernate creates a proxy and fetches the entity only when accessed
This way it saves memory and improves performance, best for large datasets to avoid unnecessary database hits

Here, an example for 1-1 bidirectional mapping is shown
Since Owner entity is LAZY
For case 1 - it will fetch data only from owner table with simple select query.
For case 2 - it will show 2 queries, first time it fetches data only from owner table
and the second time it will perform one more query from vehicle table with left join on owner table
since it is bidirectional when the getVehicle() method is called on owner object.
For case 3 - it will show 2 queries, first time it fetches data only from owner table
and the second time it will perform one more query from vehicle table with left join on owner table
since it is bidirectional when the vehicle is to be printed in the toString() method of owner object

If we do not initialize the proxy, using Hibernate.initialize()
it will throws LazyInitializationException if accessed outside session.
You can try by commenting out Hibernate.initialize() from ownerRepoImpl

EAGER loading - can cause performance issues, it loads everything immediately!
The related entity will be loaded immediately along with main entity
Uses JOIN in SQL, which can lead to heavy queries.
Can lead to huge unnecessary queries if the related entity is large.

Since Vehicle entity is EAGER
- each time the data is fetched from vehicle entity ,
a query with join on owner table (bidirectional) will be executed to fetch data from both the tables

When to use when?
- For small entities and most of the operations require data from all the related entities, eager is best
- If most operations need data individually belonging to the table and not the related entity, lazy is best
- For large datasets, lazy is best since it improves performance, saves memory and avoids heavy queries with joins
* */

public class App {
    public static void main(String[] args) {
        OwnerService os = new OwnerServiceImpl(new OwnerRepoImpl());
        VehicleService vs = new VehicleServiceImpl(new VehicleRepoImpl());
        boolean flag = true;
        System.out.println("WELCOME TO VIEW THE DEMO FOR EAGER AND LAZY LOADING!");
        os.insertManualData();
        try (Scanner sc = new Scanner(System.in);) {
            while (flag) {
                System.out.println("Make a choice between following options: ");
                System.out.println("1. Fetch Owner Details By Owner ID");
                System.out.println("2. Fetch Vehicle Details By Owner ID");
                System.out.println("3. Fetch All Details By Owner ID");
                System.out.println("4. Fetch Owner Details By Vehicle ID");
                System.out.println("5. Fetch Vehicle Details By Vehicle ID");
                System.out.println("6. Fetch All Details By Vehicle ID");
                System.out.println("7. Exit");
                if (sc.hasNextInt()) {
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1: {
                            int ownerId = InputUtil.acceptId(sc);
                            Person p = os.fetchOwnerDetailsOnlyByOwnerId(ownerId);
                            if (p != null) {
                                System.out.println(p.toString(false));
                            }else {
                                System.out.println("Owner not found!");
                            }
                        }
                        break;
                        case 2:
                        {
                            int ownerId = InputUtil.acceptId(sc);
                            Person p = os.fetchVehicleDetailsOnlyByOwnerId(ownerId);
                            if (p != null) {
                                Vehicle v = p.getVehicle();
                                System.out.println(v);
                            }else {
                                System.out.println("Owner not found!");
                            }
                        }
                        break;
                        case 3:
                        {
                            int ownerId = InputUtil.acceptId(sc);
                            Person p = os.fetchAllDetailsByOwnerId(ownerId);
                            if (p != null) {
                                System.out.println(p.toString(true));
                            }else {
                                System.out.println("Owner not found!");
                            }
                        }
                            break;
                        case 4:
                        {
                            int vehicleId = InputUtil.acceptId(sc);
                            Vehicle v = vs.fetchOwnerDetailsOnlyByVehicleId(vehicleId);
                            if (v != null) {
                                Person p = v.getOwner();
                                System.out.println(p.toString(false));
                            }else {
                                System.out.println("Vehicle not found!");
                            }
                        }
                        break;
                        case 5:
                        {
                            int vehicleId = InputUtil.acceptId(sc);
                            Vehicle v = vs.fetchVehicleDetailsOnlyByVehicleId(vehicleId);
                            if (v != null) {
                                System.out.println(v);
                            }else {
                                System.out.println("Vehicle not found!");
                            }
                        }
                        break;
                        case 6:
                        {
                            int vehicleId = InputUtil.acceptId(sc);
                            Vehicle v = vs.fetchAllDetailsByVehicleId(vehicleId);
                            if (v != null) {
                                System.out.println(v.toString(true));
                            }else {
                                System.out.println("Vehicle not found!");
                            }
                        }
                        break;
                        case 7:
                            flag = false;
                            break;
                        default:
                            System.out.println("Invalid choice! Enter again.");
                    }
                } else {
                    System.out.println("Invalid choice! Enter again.");
                    sc.next();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("THANK YOU!");
        HibernateUtil.shutdown();
    }
}
