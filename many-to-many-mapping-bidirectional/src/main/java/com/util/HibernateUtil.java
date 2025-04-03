package com.util;

import com.entity.Course;
import com.entity.Student;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory = null;
    private static StandardServiceRegistry registry = null;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            try{
                registry = new StandardServiceRegistryBuilder().build();
                MetadataSources metadataSources = new MetadataSources(registry).addAnnotatedClass(Course.class).addAnnotatedClass(Student.class);
                Metadata metadata = metadataSources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }catch(Exception e){
                if(registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
        if(registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
