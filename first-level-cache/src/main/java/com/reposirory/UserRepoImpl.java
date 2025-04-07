package com.reposirory;

import com.entity.User;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

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
    public List<User> getUserListByUsingGetForEachInSingleSession(List<Integer> ids) {
        try (Session session = sessionFactory.openSession()) {
            return ids.stream().map(userId -> {
                return session.get(User.class, userId);
            }).toList();
        }
    }

    @Override
    public List<User> getUserListByFetchingEachInDifferentSession(List<Integer> ids) {
       List<User> users = new ArrayList<>();
       ids.forEach(userId -> {
           User user = getUserById(userId);
           users.add(user);
       });
       return users;
    }

    private User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }


    // We are not using in clause here because hibernate
    // will not fetch the repeated ids in the list again
    // and output will be given for distinct ids only
    // Here
    @Override
    public List<User> getUserListByUsingHql(List<Integer> ids) {
        String hql = "SELECT u FROM User u WHERE u.id = :id";
        try (Session session = sessionFactory.openSession()) {
            return ids.stream().map(userId->{
                return session.createSelectionQuery(hql, User.class)
                        .setParameter("id", userId)
                        .getSingleResultOrNull();
            }).toList();
        }
    }

}
