package com.repository;

import com.entity.Person;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PersonRepoImpl implements PersonRepo {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void savePerson(Person person) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updatePerson(Person person, int personId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, personId);
            if(p != null) {
                if(person.getName()!=null) p.setName(person.getName());
                if(person.getContact()!=null) p.setContact(person.getContact());
                session.merge(p);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Person getPersonById(int personId) {
       try(Session session = sessionFactory.openSession()) {
           return session.get(Person.class, personId);
       }catch (Exception e) {
           throw new RuntimeException("Error fetching person details",e);
       }
    }
}
