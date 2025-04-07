package com.repository;

import com.entity.Clothing;
import com.entity.Customer;
import com.entity.Electronics;
import com.entity.Product;
import com.util.HibernateUtil;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerRepoImpl implements CustomerRepo{
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    // CRITERIA API DOES NOT SUPPORT INSERTION.
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
            // get the criteria builder
           CriteriaBuilder builder = session.getCriteriaBuilder();
           // create criteria query for the object class to fetch
           CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
           // define the root (from clause)
           Root<Customer> root = query.from(Customer.class);
           // apply where clause
           query.select(root).where(builder.equal(root.get("id"), customerId));
           // return query
           return session.createQuery(query).uniqueResult();
        }
    }

    // this query will fetch product details eagerly
    @Override
    public Customer getCustomerWithProductDetails(int customerId) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            root.fetch("product", JoinType.LEFT); // fetch eagerly
            query.select(root).where(builder.equal(root.get("id"),  customerId));
            return session.createQuery(query).uniqueResult();
        }
    }

    @Override
    public Object getCustomerName(int customerId) {
        try(Session session = sessionFactory.openSession()){
           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<String> query = builder.createQuery(String.class); // to fetch only string
           Root<Customer> root = query.from(Customer.class);
           query.select(root.get("name")).where(builder.equal(root.get("id"), customerId));
           return session.createQuery(query).uniqueResult();
        }
    }

//    public List<String[]> getCustomerAndProductNames() {
//        try (Session session = sessionFactory.openSession()) {
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Tuple> query = cb.createTupleQuery();
//
//            Root<Customer> root = query.from(Customer.class);
//            Join<Customer, Product> productJoin = root.join("product", JoinType.LEFT);
//
//            query.multiselect(
//                    root.get("name").alias("customerName"),
//                    productJoin.get("productName").alias("productName")
//            );
//
//            List<Tuple> result = session.createQuery(query).getResultList();
//
//            // Convert Tuple to List<String[]>
//            List<String[]> names = new ArrayList<>();
//            for (Tuple tuple : result) {
//                String customerName = tuple.get("customerName", String.class);
//                String productName = tuple.get("productName", String.class);
//                names.add(new String[]{customerName, productName});
//            }
//
//            return names;
//        }
//    }

// when updating with criteria api, it executes direct sql on the db
// without loading the entity into the persistence context
// hibernate won't track changes in the entity, no dirty checking will happen since no entity is loaded
// and no cascade updates will occur
// If you’re doing bulk updates, or you don't rely on
// Hibernates management (cascade, orphan removal, etc.),
// you want to improve performance, use criteria API

    @Override
    public void updateCustomer(Customer customer, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Customer> update = builder.createCriteriaUpdate(Customer.class);
            Root<Customer> root = update.from(Customer.class);
            update.set(root.get("name"), customer.getName());
            update.where(builder.equal(root.get("id"), customerId));
            session.createMutationQuery(update).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    // here cascade operations will not work ,
    // and orphan removal will also not work when using criteria API
    public void deleteCustomer(int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<Customer> root = query.from(Customer.class);
            query.select(root.get("product").get("id")).where(builder.equal(root.get("id"), customerId));
            Integer productId = session.createQuery(query).uniqueResult();

            // removes foreign key reference
            CriteriaDelete<Customer> delete = builder.createCriteriaDelete(Customer.class);
            Root<Customer> customerRoot = delete.from(Customer.class);
            delete.where(builder.equal(customerRoot.get("id"), customerId));
            session.createMutationQuery(delete).executeUpdate();

            if(productId != null){
                CriteriaDelete<Product> pdelete = builder.createCriteriaDelete(Product.class);
                Root<Product> productRoot = pdelete.from(Product.class);
                pdelete.where(builder.equal(productRoot.get("id"), productId));
                session.createMutationQuery(pdelete).executeUpdate();
            }

            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }


    // rewriting this method in criteria api -
    // adds more code
    // loses automatic cascade behaviour
    // requires extra persistence steps
    // more readable than hql
    @Override
    public void updateCustomerProduct(Product product, int customerId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            root.fetch("product", JoinType.LEFT);
            query.select(root).where(builder.equal(root.get("id"), customerId));
            Customer existingCustomer = session.createQuery(query).uniqueResult();
            System.out.println(existingCustomer);
            if(existingCustomer != null){
                Product existingProduct = existingCustomer.getProduct();
                if(!existingProduct.getClass().equals(product.getClass())){
                   System.out.println("entered here");
//                    Hibernate’s CriteriaUpdate does not allow setting associations to null reliably via .set("product", null)
//                    — especially when it’s a mapped entity relationship (like @OneToOne, @ManyToOne, etc.).
//                    Hibernate expects a managed entity or non-null value for associations during bulk updates.
                    String hql = "update Customer c set c.product = null where c.id=:id";
                    session.createMutationQuery(hql).setParameter("id", customerId).executeUpdate();
                    System.out.println("Error setting null");
                    CriteriaDelete<Product> pdelete = builder.createCriteriaDelete(Product.class);
                    Root<Product> productRoot = pdelete.from(Product.class);
                    pdelete.where(builder.equal(productRoot.get("id"), existingProduct.getId()));
                    session.createMutationQuery(pdelete).executeUpdate();
                    System.out.println("Error deleting product");
                    session.persist(product);
                    System.out.println("Error persisting product");
                    CriteriaUpdate<Customer> updateNewProduct = builder.createCriteriaUpdate(Customer.class);
                    Root<Customer> updateCustomerRoot = updateNewProduct.from(Customer.class);
                    updateNewProduct.set("product", product );
                    updateNewProduct.where(builder.equal(updateCustomerRoot.get("id"), customerId));
                    session.createMutationQuery(updateNewProduct).executeUpdate();
                    System.out.println("Error updating new product");
                }else{
                    CriteriaUpdate<Product> updateCommon = builder.createCriteriaUpdate(Product.class);
                    Root<Product> productRoot = updateCommon.from(Product.class);
                    String nameToSet = product.getProductName() != null ? product.getProductName() : existingProduct.getProductName();
                    double priceToSet = product.getPrice() != 0 ? product.getPrice() : existingProduct.getPrice();
                    updateCommon.set("productName", nameToSet);
                    updateCommon.set("price", priceToSet);
                    updateCommon.where(builder.equal(productRoot.get("id"), existingProduct.getId()));
                    session.createMutationQuery(updateCommon).executeUpdate();

                    if (product instanceof Electronics && existingProduct instanceof Electronics) {
                        int wp = ((Electronics) product).getWarrantyPeriod();
                        if(wp > 0){
                            CriteriaUpdate<Electronics> update = builder.createCriteriaUpdate(Electronics.class);
                            Root<Electronics> eRoot = update.from(Electronics.class);
                            update.set("warrantyPeriod", wp);
                            update.where(builder.equal(eRoot.get("id"), existingProduct.getId()));
                            session.createMutationQuery(update).executeUpdate();
                        }
                    } else if (product instanceof Clothing && existingProduct instanceof Clothing) {
                        String size = ((Clothing) product).getSize();
                        if(size != null){
                           CriteriaUpdate<Clothing> update = builder.createCriteriaUpdate(Clothing.class);
                           Root<Clothing> clothingRoot = update.from(Clothing.class);
                           update.set("size", size);
                           update.where(builder.equal(clothingRoot.get("id"), existingProduct.getId()));
                           session.createMutationQuery(update).executeUpdate();
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
           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
           Root<Customer> root = query.from(Customer.class);
           query.select(root);
           return session.createQuery(query).getResultList();
        }
    }

    @Override
    public List<Customer> getAllCustomersWithProductDetails() {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            root.fetch("product", JoinType.LEFT);
            query.select(root);
            return session.createQuery(query).getResultList();
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

//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Product> query = builder.createQuery(Product.class);
//            Root<Product> root = query.from(Product.class);
//            query.select(root);

            // the n+1 problem can be rectified by using join fetch which
            // forces to eagerly fetch product with customers in one query only

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            root.fetch("customer", JoinType.LEFT);
            query.select(root);

            return session.createSelectionQuery(query).getResultList();
        }
    }

    @Override
    public List<Customer> getCustomersWithElectronicProducts() {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            root.fetch("product", JoinType.LEFT);
            Join<Customer, Product> join = root.join("product", JoinType.INNER);

            query.select(root).where(builder.isNotNull(builder.treat(join, Electronics.class)));
            return session.createQuery(query).getResultList();
        }
    }

    @Override
    public List<Customer> getCustomersWithProductPriceGreaterThanThousand() {
        try(Session session = sessionFactory.openSession()){
           CriteriaBuilder builder = session.getCriteriaBuilder();
           CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
           Root<Customer> root = query.from(Customer.class);
           root.fetch("product", JoinType.LEFT);
           query.select(root).where(builder.greaterThan(root.get("product").get("price"), 1000));
           return session.createQuery(query).getResultList();
        }
    }

    @Override
    public List<Customer> getPaginatedCustomersWithProducts(int pageNo) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
            Root<Customer> root = query.from(Customer.class);
            query.select(root);
            return session.createQuery(query)
                    .setFirstResult((pageNo - 1) * 10)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    @Override
    public int getCountOfProductType(char type) {
        try(Session session = sessionFactory.openSession()){
            Class<? extends Product> productType = type == 'E' ? Electronics.class : Clothing.class;
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            Root<Product> root = query.from(Product.class);
            Root<? extends Product> treatedRoot = builder.treat(root, productType);
            query.select(builder.count(treatedRoot))
                    .where(builder.equal(treatedRoot.type(), productType));
            return session.createQuery(query).getSingleResult().intValue();
        }
    }
}
