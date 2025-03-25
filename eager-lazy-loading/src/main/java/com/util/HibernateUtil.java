package com.util;

import com.entity.Person;
import com.entity.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private HibernateUtil() {}

    private static SessionFactory sessionFactory = null;
    private static StandardServiceRegistry registry = null;

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Vehicle.class).addAnnotatedClass(Person.class);
                Metadata md = sources.getMetadataBuilder().build();
                sessionFactory = md.getSessionFactoryBuilder().build();
            }
            catch(Exception e){
                System.out.println("Error in creating session factory: " + e.getMessage());
            };
        }
        return sessionFactory;
    }

    public static void shutdown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
        if (registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
