package com.controller;

import com.com.enums.Gender;
import com.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HomeController {
    public static void main(String[] args) {
        Configuration cfg = new Configuration()
//                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
//                .setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/test")
//                .setProperty("hibernate.connection.username", "root")
//                .setProperty("hibernate.connection.password","root")
//                .setProperty("hibernate.hbm2ddl.auto", "create")
//                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(Student.class);

//        This will automatically configure all the properties mentioned in hibernate.properties file
//                and map it to the entity, either way, you can use setProperty() method

        SessionFactory sf = cfg.buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Student student = new Student();
        student.setRollNo(101);
        student.setName("Jack");
        student.setAge(15);
        student.setGender(Gender.M);

        session.save(student);
        tx.commit();
        session.close();
        sf.close();

        System.out.println("Done!");

    }
}
