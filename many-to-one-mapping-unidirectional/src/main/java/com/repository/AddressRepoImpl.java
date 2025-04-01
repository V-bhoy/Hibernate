package com.repository;

import com.entity.Address;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AddressRepoImpl implements AddressRepo {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void saveAddress(List<Address> addresses) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            for(Address address : addresses) {
                System.out.println(address);
                session.persist(address);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateAddress(Address address, int addressId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Address existingAddress = session.get(Address.class, addressId);
            if (existingAddress != null) {
                if(address.getStreet()!=null) existingAddress.setStreet(address.getStreet());
                if(address.getCity()!=null) existingAddress.setCity(address.getCity());
                session.merge(existingAddress);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteAddress(int addressId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Address existingAddress = session.get(Address.class, addressId);
            if (existingAddress != null) {
                session.remove(existingAddress);
            }
            tx.commit();
        }catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Address getAddressById(int addressId) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Address.class, addressId);
        }
    }

}
