package com.util;


import com.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory = null;

//    if you do not want to use hibernate.properties file --> you can also do this
// public static SessionFactory getHibernateSessionFactory() {
//    if(sf==null) {
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//        map.put(Environment.URL, "jdbc:mysql://localhost:3306/test");
//        map.put(Environment.USER, "root");
//        map.put(Environment.PASS, "root");
//
//        map.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        map.put(Environment.HBM2DDL_AUTO, "create");
//        map.put(Environment.SHOW_SQL, true);
//
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(map).build();
//        MetadataSources mds = new MetadataSources(registry).addAnnotatedClass(Person.class).addAnnotatedClass(Aadhar.class);
//        Metadata md = mds.getMetadataBuilder().build();
//        sf = md.getSessionFactoryBuilder().build();
//        return sf;
//    }
//    return sf;
//}

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties")){
                Properties properties = new Properties();
                properties.load(input);
                Configuration configuration = new Configuration().addProperties(properties).addAnnotatedClass(User.class); // Reads hibernate.properties
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
