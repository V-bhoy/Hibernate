package com.repository;

import com.entity.Person;
import com.entity.Vehicle;
import com.enums.VehicleType;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OwnerRepoImpl implements OwnerRepo {
   SessionFactory sf = HibernateUtil.getSessionFactory();
    @Override
    public void insertManualData() {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            for(int i=0; i<3; i++){
                Person p = new Person();
                Vehicle v = new Vehicle();
                p.setName("Nia");
                p.setAddress("Mumbai");
                v.setType(i%2 == 0 ? VehicleType.CAR : VehicleType.BIKE);
                v.setModel("MODEL"+i);
                v.setVehicleNumber("XXXX"+i);
                p.setVehicle(v);
                session.persist(p);
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
    public Person fetchDetails(int ownerId) {
        try (Session session = sf.openSession()) {
            Person p = session.get(Person.class, ownerId);
            Hibernate.initialize(p.getVehicle());
            return p;
        }
    }

}
