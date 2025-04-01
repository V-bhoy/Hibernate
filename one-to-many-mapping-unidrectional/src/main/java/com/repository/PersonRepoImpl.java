package com.repository;

import com.entity.Address;
import com.entity.Person;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonRepoImpl implements PersonRepo {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public void savePerson(Person person) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            System.out.println("Saving person: " + person);
            session.persist(person);
            System.out.println("Person added successfully!");
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void registerAddress(List<Address> addresses, int personId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, personId);
            if(p != null) {
                List<Address> currAddress = p.getAddresses();
                currAddress.addAll(addresses);
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updatePerson(Person person, int personId) {
          Transaction tx = null;
          try(Session session = sf.openSession()) {
              tx = session.beginTransaction();
              Person p = session.get(Person.class, personId);
              if(p != null) {
                  if(person.getName()!=null) p.setName(person.getName());
                  if(person.getContact()!=null) p.setContact(person.getContact());
                  session.merge(p);
              }
              tx.commit();
          }catch (Exception e) {
              if(tx != null) {
                  tx.rollback();
              }
              throw e;
          }
    }

    @Override
    public void updateAddress(Address address, int personId, int addressId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, personId);
            if(p != null) {
               List<Address> currAddress = p.getAddresses();
               for(Address a : currAddress) {
                   if(a.getId() == addressId) {
                       if(address.getStreet()!=null) a.setStreet(address.getStreet());
                       if(address.getCity()!=null) a.setCity(address.getCity());
                       if(address.getState()!=null) a.setState(address.getState());
                       break;
                   }
               }
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deletePerson(int personId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, personId);
            if(p != null) {
                session.remove(p);
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeAddresses(List<Address> addresses, int personId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Person p = session.get(Person.class, personId);
            if(p != null) {
                List<Address> currAddress = p.getAddresses();
                Set<Integer> idsToRemove = addresses.stream().map(Address::getId).collect(Collectors.toSet());
                currAddress.removeIf(address -> idsToRemove.contains(address.getId()));
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Person getPerson(int personId) {
        try(Session session = sf.openSession()) {
            Person p = session.get(Person.class, personId);
            if(p != null) {
                Hibernate.initialize(p.getAddresses());
            }
            return p;
        }
    }
}
