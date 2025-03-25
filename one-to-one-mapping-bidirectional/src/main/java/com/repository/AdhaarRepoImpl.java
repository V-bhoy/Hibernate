package com.repository;

import com.entity.AdhaarCard;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AdhaarRepoImpl implements AdhaarRepo{
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public AdhaarCard getDetailsByAdhaarId(int adhaarId){
        try (Session session = sf.openSession()) {
            return session.get(AdhaarCard.class, adhaarId);
        }
    }
}
