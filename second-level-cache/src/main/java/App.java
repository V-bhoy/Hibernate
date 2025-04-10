// SECOND LEVEL CACHE
// it is a caching mechanism that stores objects across sessions,
// unlike the first-level cache which is session-specific.
// The goal of second-level cache is to reduce database access by storing entity data,
// collections, and queries in a shared cache (across multiple sessions/factory).
// It is optional and needs to be explicitly configured. It is disabled by default
//It is SessionFactory-level, meaning all sessions created by the factory can use the cache.
// It caches data after the first session loads it from the DB, making future retrievals faster
// without hitting the DB again.
// To enable second level cache -
// add dependencies for hibernate-jcache and cache provider like Ehcache, Infinispan, Hazelcast, or OSCache.
// we used ehcache in the demo and the version of jcache should be same as hibernate core
// add hibernate configuration properties
// hibernate.cache.use_second_level_cache=true
// hibernate.cache.region.factory_class=org.hibernate.cache.ehcache.EhCacheRegionFactory
// hibernate.cache.use_query_cache=true
// the use second level cache property is optional as it is set to true by default
// as soon as the region factory class for the cache is set on adding the dependencies
// to cache hql queries or criteria api , you need to set use query cache property to true
// for caching the entities at second level, you need to use annotation @Cacheable,
// which is inherited by child classes as well
// set the CacheConcurrencyStrategy by using @Cache annotation with attribute usage
// it is an annotation-level configuration in Hibernate used to define
// how the second-level cache should manage concurrent access to cached entities
// — basically how it handles consistency, transactions, and updates.
// 🔸 Why Do We Need It?
//  When multiple users/sessions read and write the same cached entity:
//	•	How do you keep the cache consistent with the database?
//	•	What should happen if one session updates the DB but another reads stale data from the cache?
//That’s where CacheConcurrencyStrategy comes into play.
// There are four strategies -
// READ_ONLY - No updates allowed. Fastest, safest for immutable data
// If you try to modify the entity and commit the transaction → Hibernate will throw an exception.
// No updates allowed, neither in the database nor in the cache.
// NONSTRICT_READ_WRITE - Updates allowed but no strict guarantee of consistency. May serve stale data temporarily.
// READ_WRITE - Uses versioning to keep cache consistent with DB. Slower but ensures data integrity.
// TRANSACTIONAL - Full ACID-compliant transactions using JTA. Needs a fully transactional cache provider.
// If your entity is never modified after creation → go with READ_ONLY.
// If it’s read often and modified rarely → NONSTRICT_READ_WRITE is a good balance.
// For frequent reads and writes, prefer READ_WRITE.
// Good for -
// Frequently read, rarely updated data (e.g., product catalog, user profiles).
// Data shared across sessions.
// Read-heavy applications.
// Avoid if:
// Data changes often and consistency is critical.
//	You don’t want the complexity of cache invalidation.

// PROCESS
//  First: Hibernate checks first-level cache.
//	If not found → it checks second-level cache.
//	If still not found → it hits the DB, and caches the entity in both first-level and second-level caches.
// when update happens , hibernate marks the cached entity dirty in first level
// updates the db, replaces the cached entity in second level with new entity on incrementing the entity version
// the session cache is cleared upon session.close() or clear()

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
                        // only 2 select queries will be executed, for id 1 and 2 only
                        // the user for id 1 will be cached in the session as well as session factory
                        // it will be fetched from the cache in the o/p
                       List<User> uList = u.getUserListByUsingGetForEachInSingleSession(List.of(1,2,1));
                       for (User user : uList) {
                           System.out.println(user);
                       }
                    }
                    break;
                    case 2: {
                        // restart the app again, otherwise no queries will be executed due to already cached entities
                        // since for fetching each user, a new session is created and closed
                        // the cache is bound to the session each time the user is fetched
                        // but this time 2 select queries will be executed each time the user is fetched for 1 and 2
                        // although user 1 is again fetched in a new session, it is cached at second level
                        // hence there is no DB call
                        List<User> uList = u.getUserListByFetchingEachInDifferentSession(List.of(1,2,1));
                        for (User user : uList) {
                            System.out.println(user);
                        }
                    }
                    break;
                    case 3: {
                        // Here HQL is used for fetching the user
                        // HQL and criteria API do not support first level cache,
                        // but it supports second level cache when configured with use_query_cache property
                        // and setCacheable(true) on hql query in session
                        // Hence 3 select queries will be executed if not configured for second level cache
                        // otherwise 2 select queries will be executed since the repeated one will be cached
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
