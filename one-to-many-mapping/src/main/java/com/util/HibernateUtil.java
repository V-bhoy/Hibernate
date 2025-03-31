package com.util;

import com.entity.Author;
import com.entity.Book;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private HibernateUtil(){

    }
    private static SessionFactory sessionFactory = null;
    private static StandardServiceRegistry registry = null;

    public static synchronized SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                registry = new StandardServiceRegistryBuilder().build();
                MetadataSources mds = new MetadataSources(registry).addAnnotatedClass(Book.class).addAnnotatedClass(Author.class);
                Metadata md = mds.getMetadataBuilder().build();
                sessionFactory = md.getSessionFactoryBuilder().build();
            }catch(Exception e){
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
                if(registry != null){
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutDown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
        if(registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
