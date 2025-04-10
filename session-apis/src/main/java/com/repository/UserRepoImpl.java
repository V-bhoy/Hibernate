package com.repository;

import com.entity.User;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
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
            session.persist(user);
            tx.commit();
        }catch(Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public User getUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Querying....");
            User user = session.get(User.class, userId);
            System.out.println("The query is executed immediately.");
            return user;
        }
    }

    @Override
    public User loadUser(int userId, boolean needDetails) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Querying....");
            User user = session.load(User.class, userId);
            System.out.println("The query is not executed immediately.");
            if(needDetails) {
                System.out.println("Class of the proxy");
                System.out.println(user.getClass());
                System.out.println("Initializing proxy");
                Hibernate.initialize(user);
                System.out.println("The query is executed");
                System.out.println("Unwrapping proxy");
                user = (User)Hibernate.unproxy(user);
                System.out.println(user.getClass());
            }
            return user;
        }
    }

}
