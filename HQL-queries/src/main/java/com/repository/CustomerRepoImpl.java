package com.repository;

import com.entity.Clothing;
import com.entity.Customer;
import com.entity.Electronics;
import com.entity.Product;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerRepoImpl implements CustomerRepo{
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // HQL does not support INSERT INTO ... VALUES like in SQL.
    // HQL only supports insert-from-select for copying data between entities
    // INSERT INTO NewCustomer(name, email) SELECT c.name, c.email FROM OldCustomer c
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


    // This is lazily loaded
    // if the lazy field is called in toString() of entity it will throw LazyInitializationException
    // best practice is to not use lazy field in toString() or use Hibernate.isInitialized() for safe display
    @Override
    public Customer getCustomer(int customerId) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c WHERE c.id = :customerId";
            return session.createSelectionQuery(hql, Customer.class)
                    .setParameter("customerId", customerId).getSingleResultOrNull();
        }
    }

    // this query will fetch product details eagerly
    @Override
    public Customer getCustomerWithProductDetails(int customerId) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product WHERE c.id = :customerId";
            return session.createSelectionQuery(hql, Customer.class)
                    .setParameter("customerId", customerId).getSingleResultOrNull();
        }
    }

    @Override
    public Object getCustomerName(int customerId) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c.name FROM Customer c WHERE c.id = :customerId";
            return session.createQuery(hql, Object.class).setParameter("customerId", customerId).getSingleResultOrNull();
        }
    }


    // when updating with hql query, it executes direct sql on the db
  // without loading the entity into the persistence context
  // hibernate won't track changes in the entity, no dirty checking will happen
  // and no cascade updates will occur
  // If you’re updating complex entities, or you rely on
  // Hibernates management (cascade, orphan removal, etc.), stick to session APIs.
    // here the association between customer & product is untouched,
    // so it would not affect the relationship

    @Override
    public void updateCustomer(Customer customer, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            if(customer.getName() != null){
                String hql = "UPDATE Customer c SET c.name = :name WHERE c.id = :customerId";
                session.createMutationQuery(hql)
                        .setParameter("name", customer.getName())
                        .setParameter("customerId", customerId)
                        .executeUpdate();
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    // here cascade operations will not work , and orphan removal will also not work when using hql
    // You do NOT need to write an explicit JOIN in HQL when accessing c.product.id.
    // Hibernate will generate the correct JOIN in SQL behind the scenes.
    // If you’re accessing customer.getProduct().getId() in Java without initializing it,
    // you’ll get a LazyInitializationException outside a session
    public void deleteCustomer(int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            String productHql = "SELECT c.product.id FROM Customer c WHERE c.id = :customerId";
            Integer productId = session.createSelectionQuery(productHql, Integer.class)
                    .setParameter("customerId", customerId).uniqueResult();
            if(productId != null){
                String hql = "DELETE FROM Product p WHERE p.id = :productId";
                session.createMutationQuery(hql).setParameter("productId", productId).executeUpdate();
            }
            String hql = "DELETE FROM Customer c WHERE c.id = :customerId";
            session.createMutationQuery(hql).setParameter("customerId", customerId).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }


    // rewriting this method in hql -
    // adds more code
    // loses automatic cascade behaviour
    // requires extra persistence steps
    // fails with polymorphism and relationships
    @Override
    public void updateCustomerProduct(Product product, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            String customerHql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product WHERE c.id = :customerId";
            Customer existingCustomer = session.createSelectionQuery(customerHql, Customer.class)
                    .setParameter("customerId", customerId)
                    .getSingleResultOrNull();
            if(existingCustomer != null){
                Product existingProduct = existingCustomer.getProduct();
                if(!existingProduct.getClass().equals(product.getClass())){
                    String updateCustomerProduct = "UPDATE Customer c SET c.product = :product WHERE c.id = :id";
                    String deleteProductHQL = "DELETE FROM Product p WHERE p.id = :id";
                    session.createMutationQuery(updateCustomerProduct).setParameter("product", null).setParameter("id", customerId).executeUpdate();
                    session.createMutationQuery(deleteProductHQL)
                            .setParameter("id", existingProduct.getId())
                            .executeUpdate();
                    session.persist(product);
                    session.createMutationQuery(updateCustomerProduct)
                            .setParameter("product", product)
                            .setParameter("id", customerId)
                            .executeUpdate();
                }else{
                    String updateCommon = "UPDATE Product p SET p.productName = :name, p.price = :price WHERE p.id = :id";
                    session.createMutationQuery(updateCommon)
                            .setParameter("name", product.getProductName()!=null ? product.getProductName() : existingProduct.getProductName())
                            .setParameter("price", product.getPrice()!=0 ? product.getPrice() : existingProduct.getPrice())
                            .setParameter("id", existingProduct.getId())
                            .executeUpdate();
                    if (product instanceof Electronics && existingProduct instanceof Electronics) {
                        int wp = ((Electronics) product).getWarrantyPeriod();
                        if(wp > 0){
                            String updateElectronics = "UPDATE Electronics e SET e.warrantyPeriod = :wp WHERE e.id = :id";
                            session.createMutationQuery(updateElectronics)
                                    .setParameter("wp", wp )
                                    .setParameter("id", existingProduct.getId())
                                    .executeUpdate();
                        }
                    } else if (product instanceof Clothing && existingProduct instanceof Clothing) {
                        String size = ((Clothing) existingProduct).getSize();
                        if(size != null){
                            String updateClothing = "UPDATE Clothing c SET c.size = :size WHERE c.id = :id";
                            session.createMutationQuery(updateClothing)
                                    .setParameter("size", size)
                                    .setParameter("id", existingProduct.getId())
                                    .executeUpdate();
                        }
                    }
                }
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try(Session session = sessionFactory.openSession()){
            String hql = "FROM Customer";
            return session.createSelectionQuery(hql, Customer.class).getResultList();
        }
    }

    @Override
    public List<Customer> getAllCustomersWithProductDetails() {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product";
            return session.createSelectionQuery(hql, Customer.class).getResultList();
        }
    }


    // N+1 can happen with both LAZY and EAGER
    // in cases of lazy u have to separately access product for each customer in the list
    // in cases of eager, it will fetch all products first and then fetch each customer related to
    // the product separately
    // use join fetch to avoid it
    @Override
    public List<Product> getAllProducts() {
        try(Session session = sessionFactory.openSession()){
            // this query results in N+1 query problem
            // this will query all products in a join query
            // and since each customer is associated with a product, it will query on customer table also
            // with each row, resulting in n+1 execution of queries
           //  String hql = "FROM Product";

            // the n+1 problem can be rectified by using join fetch which
            // forces to eagerly fetch product with customers in one query only
            String hql = "SELECT p FROM Product p LEFT JOIN FETCH p.customer";
            return session.createSelectionQuery(hql, Product.class).getResultList();
        }
    }

    @Override
    public List<Customer> getCustomersWithElectronicProducts() {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product p WHERE TYPE(p) = Electronics";
            return session.createSelectionQuery(hql, Customer.class).getResultList();
        }
    }

    @Override
    public List<Customer> getCustomersWithProductPriceGreaterThanThousand() {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product p WHERE p.price > 1000 ";
            return session.createSelectionQuery(hql, Customer.class).getResultList();
        }
    }

    @Override
    public List<Customer> getPaginatedCustomersWithProducts(int pageNo) {
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT c FROM Customer c LEFT JOIN FETCH c.product";
            return session.createSelectionQuery(hql, Customer.class)
                    .setFirstResult((pageNo-1)*2) // skip count
                    .setMaxResults(2)
                    .getResultList();
        }
    }

    @Override
    public int getCountOfProductType(char type) {
        try(Session session = sessionFactory.openSession()){
            Class<?> productType = type == 'E' ? Electronics.class : Clothing.class;
            String hql = "SELECT count(p) FROM Product p WHERE TYPE(p) = :productType ";
            return session.createSelectionQuery(hql, Long.class)
                    .setParameter("productType", productType)
                    .getSingleResult()
                    .intValue();
        }
    }
}
