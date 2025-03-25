package com.repository;

import com.entity.AdhaarCard;
import com.entity.Person;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PersonRepoImpl implements PersonRepo {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void registerPersonDetails(Person person) throws RuntimeException {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Person getPersonDetailsById(int id){
        try(Session session = sf.openSession()) {
            return session.get(Person.class, id);
        }
    }

    @Override
    public void updatePersonDetails(Person person, int personId) {
      Transaction tx = null;
      try(Session session = sf.openSession()) {
          tx = session.beginTransaction();
          Person p = session.get(Person.class, personId);
          if(person.getName() != null) p.setName(person.getName());
          if(person.getAddress() != null) p.setAddress(person.getAddress());
          if(person.getPhone() != null) p.setPhone(person.getPhone());
          if(person.getAge() != 0) p.setAge(person.getAge());
          if (person.getGender() != null) p.setGender(person.getGender());
          AdhaarCard adhar = person.getAdhaarCard();
          if(person.getAdhaarCard() != null) adhar.setAdhaarNo(person.getAdhaarCard().getAdhaarNo());
          session.merge(p);
          tx.commit();
      }catch (Exception e) {
          if (tx != null) {
              tx.rollback();
          }
          throw e;
      }
    }

    @Override
    public void deletePersonDetails(int id) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, id);
            if(p!=null){
                session.remove(p);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
