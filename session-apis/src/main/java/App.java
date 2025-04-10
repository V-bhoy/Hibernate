// COMMONLY USED SESSION APIS
// get() ->
// - immediately fetches entity from DB or cache and returns entity if exists otherwise null
// - use when you readily need the full object
// load() ->
// - returns a proxy without fetching the db initially
// proxy?
// - it  is a placeholder object for an entity that is loaded lazily.
// - It looks like a real object, but behind the scenes,
// it doesn’t hit the database until you access a property of that object.
// - It’s like a fake shell of the object that Hibernate creates to delay the DB call until absolutely necessary.
// - this helps in Performance optimization (avoid unnecessary DB hits), Lazy loading of associations or entities,
// Minimize memory and network overhead
// - If the session is closed, and you try to access a proxy → LazyInitializationException
// - If the entity doesn’t exist → ObjectNotFoundException when accessing
// - returns proxy if found or throws ObjectNotFoundException
// - use it when you are confident when the entity exists
// openSession() ->
// - creates new session everytime when invoked
// - need to manually manage and close the session
// getCurrentSession() ->
// - returns existing session bound to current thread
// - hibernate manages it and is automatically used in ongoing transactions & closed with transaction commit
// - used in managed environments like Spring
// - to use this, you need to add hibernate.current_session_context_class=thread in hibernate.properties
// - means, Hibernate will bind the Session to the current thread,
// and you’ll be responsible for beginning and committing a transaction manually.

import com.entity.User;
import com.repository.UserRepo;
import com.repository.UserRepoImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static User createUser(String phone){
        User user = new User();
        user.setName("John Doe");
        user.setAge(22);
        user.setAddress("Pune");
        user.setPhone(phone);
        return user;
    }
    public static void main(String[] args) {
        System.out.println("Welcome to the demo for session apis!");
        SessionFactory sf = HibernateUtil.getSessionFactory();
        UserRepo u = new UserRepoImpl();
        try (Scanner sc = new Scanner(System.in)) {
            List<User> users = new ArrayList<>();
            User u1 = createUser("1111222211");
            User u2 = createUser("2222222222");
            users.add(u1);
            users.add(u2);
           for (User user : users) {
               u.insertUser(user);
           }
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        // since each user is fetched in a single session,
                        // only 2 select queries will be executed,
                        // the user for id 1 will be cached and it will be fetched from the cache in the o/p
                       User user = u.getUser(1);
                           System.out.println(user);
                    }
                    break;
                    case 2: {
                        // since for fetching each user, a new session is created and closed
                        // the cache is bound to the session each time the user is fetched
                        // hence, 3 select queries will be executed each time the user is fetched
                        User user1 = u.loadUser(2, false);
                        System.out.println("Not yet executed the query!");
                        // This statement will throw LazyInitializationException, since we are
                        // accessing the property of lazily loaded entity outside the session
                        // System.out.println(user.getName());

                        // Hibernate dynamically generates a subclass of your entity class (like Customer) at runtime.
                        // That subclass overrides methods and adds hooks to load the real data when accessed.
                        // Either we can initialize the proxy and unwrap it within the session, when we need details
                        // or use eager method
                        // So when we initialize a proxy, it will force hibernate to load the required data from DB
                        // Even after the proxy is initialized, the object remains a proxy — it just now contains real data.
                        // Needs unwrapping if you want to be sure it’s the real class

                        // This will fetch from db when we set need details to true
                        User user2 = u.loadUser(2, true);
                        System.out.println("user1: " + user1.getClass()); // proxy is returned
                        System.out.println("user2: "+user2.getName()); // no exception thrown
                        System.out.println("user2: "+user2.getClass()); // actual class is returned due to unwrapping the proxy


                    }
                    break;
                    case 3:{
//                        Session s1 = sf.getCurrentSession();
//                        System.out.println(s1.hashCode());
                        // Without adding the hibernate property,
                        // These above lines will throw Hibernate Exception: No CurrentSessionContext configured

                        Session s2 = sf.openSession();
                        System.out.println(s2.hashCode());
                        Session s3 = sf.getCurrentSession();
                        System.out.println(s3.hashCode());
                        Session s4 = sf.openSession();
                        System.out.println(s4.hashCode());
                        Session s5 = sf.getCurrentSession();
                        System.out.println(s5.hashCode());

                        // The hash codes of s2 and s4 will be different since each time a new session is created
                        // while s3 and s5 will be equal since it's a current ongoing session that was created during s3
                        // The session returned by getCurrentSession() is bound to the current thread
                        // and automatically closed at the end of the transaction if a transaction is started.
                        // For sessions returned by openSession() its needs to be manually closed
                    }
                    break;
                    default:
                        System.out.println("Please select a valid option!");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HibernateUtil.shutdown();
        System.out.println("THANK YOU !");
    }
}
