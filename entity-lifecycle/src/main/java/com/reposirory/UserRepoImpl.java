package com.reposirory;

import com.entity.User;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserRepoImpl implements UserRepo {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override

    // one insert query is executed
    public void insertUser(User user) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            if(session.contains(user)) {
                System.out.println("User is in PERSISTENT state!");
            }else{
                System.out.println("User is in TRANSIENT state!");
            }
            session.persist(user);
            if(session.contains(user)) {
                System.out.println("User is in PERSISTENT state!");
            }else{
                System.out.println("User is in TRANSIENT state!");
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    // select query is executed
    @Override
    public User getUser(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    // select query is executed when get() is invoked ,
    // due to which u is in the persistent state
    // hibernate tracks any changes to the entity and is reflected in the DB on commit
    // hence, merge() is not needed
    @Override
    public void updateUser(User user, int id) {
          Transaction tx = null;
          try (Session session = sessionFactory.openSession()) {
              tx = session.beginTransaction();
              User u = session.get(User.class, id);
              if(u != null) {
                  u.setName(user.getName());
                  u.setAddress(user.getAddress());
                  u.setAge(user.getAge());
                  if(session.contains(u)) {
                      System.out.println("User is in PERSISTENT state!");
                  }else{
                      System.out.println("User is in DETACHED state!");
                  }
                  // session.merge(u); Even if you add merge here,
                  // hibernate will automatically track the changes and execute only one update query
              }

              tx.commit();
          }catch(Exception e) {
              if(tx != null) tx.rollback();
              throw e;
          }
    }

    // select query is executed when get() is invoked ,
    // due to which u is in the persistent state
    // hibernate tracks any changes to the entity
    // When evict() method is invoked, the entity is no longer in the persistent context
    // hibernate does not track changes to it anymore, and the changes won't reflect even on commit()
    // When merge() is invoked, the entity is fetched again from the DB, it is in PERSISTENT state
    // and then updated in the DB on commit()
    // select and update query is executed on merge()
    @Override
    public void updateUserAfterEviction(User user, int id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User u = session.get(User.class, id);
            if(u != null) {
                u.setName(user.getName());
                u.setAddress(user.getAddress());
                u.setAge(user.getAge());
                if(session.contains(u)) {
                    System.out.println("User is in PERSISTENT state!");
                }else{
                    System.out.println("User is in DETACHED state!");
                }
                session.evict(u);
                if(session.contains(u)) {
                    System.out.println("User is in PERSISTENT state!");
                }else{
                    System.out.println("User is in DETACHED state!");
                }
                session.merge(u);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    // select query is executed when get() is invoked ,
    // due to which u is in the persistent state
    // hibernate tracks any changes to the entity
    // the delete() marks the entity for deletion, its in deleted state now
    // It is deleted from the DB on commit
    @Override
    public void deleteUser(int id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null) {
                if(session.contains(user)) {
                    System.out.println("User is in PERSISTENT state!");
                }else{
                    System.out.println("User is in DELETED/ REMOVED state!");
                }
                session.remove(user);
                if(session.contains(user)) {
                    System.out.println("User is in PERSISTENT state!");
                }else{
                    System.out.println("User is in DELETED/ REMOVED state!");
                }
                tx.commit();
            }

        }catch(Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
    }
}
