// FIRST LEVEL CACHE
// Each session comes with a cache that is nothing but persistent context / primary cache
// First level cache can be defined as a session level cache which means
// all entities that are loaded, saved or updated within a single session are cached in memory
// Hibernate uses this to avoid unnecessary DB calls
// Changes made to an entity are reflected in all references inside the session maintaining consistency.
// Avoids Repeated SQL Execution for the same ID during the session lifecycle.
// FEATURES -
// 1. Enabled by default — no setup required.
// 2. It only lives for the duration of the Session.
// 3. If you close the session → the cache is gone.
// 4. If you clear the session (session.clear()), the cache is also cleared.

import com.entity.User;
import com.repository.UserRepo;
import com.repository.UserRepoImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

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
        System.out.println("Welcome to the demo of entity-lifecycle!");
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
                       List<User> uList = u.getUserListByUsingGetForEachInSingleSession(List.of(1,2,1));
                       for (User user : uList) {
                           System.out.println(user);
                       }
                    }
                    break;
                    case 2: {
                        // since for fetching each user, a new session is created and closed
                        // the cache is bound to the session each time the user is fetched
                        // hence, 3 select queries will be executed each time the user is fetched
                        List<User> uList = u.getUserListByFetchingEachInDifferentSession(List.of(1,2,1));
                        for (User user : uList) {
                            System.out.println(user);
                        }
                    }
                    break;
                    case 3: {
                        // Here HQL is used for fetching the user
                        // HQL and criteria API do not support first level cache
                        // They are mainly used for executing complex queries
                        // Caching is only enabled for session apis when using get / load method
                        // Hence 3 select queries will be executed even if fetched in a single session
                        List<User> uList = u.getUserListByUsingHql(List.of(1,2,1));
                        for (User user : uList) {
                            System.out.println(user);
                        }
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
