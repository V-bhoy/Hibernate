package com.repository;

import com.entity.Vehicle;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class VehicleRepoImpl implements VehicleRepo {
    SessionFactory sf = HibernateUtil.getSessionFactory();
    @Override
    public Vehicle fetchDetailsByVehicleId(int vehicleId) {
        try (Session session = sf.openSession()) {
            return session.get(Vehicle.class, vehicleId);
        }
    }
}
