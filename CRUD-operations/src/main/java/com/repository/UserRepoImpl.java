package com.repository;

import com.entity.User;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserRepoImpl implements UserRepo {
    private final SessionFactory sf = HibernateUtil.getSessionFactory();
    @Override
    public Boolean insertUser(User user) {
        Transaction tx = null;
        try(Session session = sf.openSession()){
            tx = session.beginTransaction();
            // logic
            System.out.println("\nInserting user: " + user.toString());
            session.persist(user);
            System.out.println("\nInserted user: " + user.toString());
            tx.commit();
            System.out.println("\nCommitted transaction");
            return true;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(int userId) {
        try(Session session = sf.openSession()){
            // logic
            return session.get(User.class, userId);
        }
    }

    @Override
    public Boolean updateUser(User user) {
        Transaction tx = null;
        try(Session session = sf.openSession()){
            tx = session.beginTransaction();
            // logic
            User existingUser = session.get(User.class, user.getId());
            if(existingUser == null){
                return false;
            }
            if(user.getName() != null && !user.getName().isEmpty()){
                existingUser.setName(user.getName());
            }
            if(user.getAddress() != null && !user.getAddress().isEmpty()){
                existingUser.setAddress(user.getAddress());
            }
            if(user.getEmail() != null && !user.getEmail().isEmpty()){
                existingUser.setEmail(user.getEmail());
            }
            if(user.getContact() != null && !user.getContact().isEmpty()){
                existingUser.setContact(user.getContact());
            }
            session.merge(existingUser);
            tx.commit();
            return true;
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteUser(int userId) {
        Transaction tx = null;
        try(Session session = sf.openSession()){
            tx = session.beginTransaction();
            // logic
            User existingUser = session.get(User.class, userId);
            if(existingUser == null){
                return false;
            }
            session.remove(existingUser);
            tx.commit();
            return true;
        }catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
