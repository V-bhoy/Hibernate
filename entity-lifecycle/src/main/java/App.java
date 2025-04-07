// ENTITY LIFECYCLE
// When an entity interacts with a session, it goes through various lifecycle states
// Each session comes with a cache that is nothing but persistent context
// Whenever we perform any methods on the session, it interacts with the persistent context
// and then with the database
// This cache / persistent context is bound to the session
// The lifecycle states can be explained through following 4 states
// 1. TRANSIENT - initial state of entity, which is not connected to any active session
// and does not even exist in the database
// 2. PERSISTENT - when we invoke persist method on session, it moves from TRANSIENT to
// PERSISTENT state or managed state, which means it exists in the persistent context and is connected
// to an active session and will be saved in the DB only when we commit the transaction
// Even when we fetch the entity from DB, using get(), it comes into PERSISTENT state,
// since it exists in the persistent context
// Hibernate tracks all the changes with the entity, when it is in its persistent state
// 3. REMOVED / DELETED - When we use remove() / delete() on the session the entity moves from
// PERSISTENT state to DELETED state, the entity is marked to be deleted but it will be deleted from the
// DB only when committed
// 4. DETACHED - when we invoke methods like evict(), clear() or close() on session,
// the entity moves into a DETACHED state, that is it does not exist in the persistent context anymore.
// We can reattach it again by using merge() on session

import com.entity.User;
import com.reposirory.UserRepo;
import com.reposirory.UserRepoImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to the demo of entity-lifecycle!");
        UserRepo u = new UserRepoImpl();
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        User user = InputUtil.acceptUserDetails(sc);
                        if (user != null) {
                            u.insertUser(user);
                            System.out.println("User added successfully!");
                        }
                    }
                    break;
                    case 2: {
                        int userId = InputUtil.acceptUserId(sc);
                        User user = u.getUser(userId);
                        if (user != null) {
                            User updateUser = InputUtil.acceptUserDetailsToUpdate(sc);
                            if (updateUser != null) {
                                u.updateUser(updateUser, userId);
                                System.out.println("User updated successfully!");
                            }
                        } else {
                            System.out.println("User not found!");
                        }
                    }
                    break;
                    case 3: {
                        int userId = InputUtil.acceptUserId(sc);
                        User user = u.getUser(userId);
                        if (user != null) {
                            User updateUser = InputUtil.acceptUserDetailsToUpdate(sc);
                            if (updateUser != null) {
                                u.updateUserAfterEviction(updateUser, userId);
                                System.out.println("User updated successfully!");
                            }
                        } else {
                            System.out.println("User not found!");
                        }
                    }
                    break;
                    case 4: {
                        int userId = InputUtil.acceptUserId(sc);
                        User user = u.getUser(userId);
                        if (user != null) {


                            u.deleteUser(userId);
                            System.out.println("User deleted successfully!");

                        } else {
                            System.out.println("User not found!");
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
