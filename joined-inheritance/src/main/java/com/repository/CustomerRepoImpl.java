package com.repository;

import com.entity.Clothing;
import com.entity.Customer;
import com.entity.Electronics;
import com.entity.Product;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CustomerRepoImpl implements CustomerRepo{
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void insertCustomer(Customer customer) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.persist(customer);
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Customer getCustomer(int customerId) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Customer.class, customerId);
        }
    }

    @Override
    public void updateCustomer(Customer customer, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            Customer existingCustomer = session.get(Customer.class, customerId);
            if(existingCustomer != null){
                existingCustomer.setName(customer.getName());
                session.merge(existingCustomer);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            Customer existingCustomer = session.get(Customer.class, customerId);
            if(existingCustomer != null){
                session.remove(existingCustomer);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void updateCustomerProduct(Product product, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            Customer existingCustomer = session.get(Customer.class, customerId);
            if(existingCustomer != null){
               Product existingProduct = existingCustomer.getProduct();
                if(!existingProduct.getClass().equals(product.getClass())){
                    session.remove(existingProduct);
                    existingCustomer.setProduct(product);
                }else{
                    if(product.getProduct_name()!=null) existingProduct.setProduct_name(product.getProduct_name());
                    if(product.getPrice()!=0) existingProduct.setPrice(product.getPrice());
                    if (product instanceof Electronics && existingProduct instanceof Electronics) {
                        ((Electronics) existingProduct).setWarrantyPeriod(
                                ((Electronics) product).getWarrantyPeriod()
                        );
                    } else if (product instanceof Clothing && existingProduct instanceof Clothing) {
                        ((Clothing) existingProduct).setSize(
                                ((Clothing) product).getSize()
                        );
                    }
                }
                session.merge(existingCustomer);
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }
}
