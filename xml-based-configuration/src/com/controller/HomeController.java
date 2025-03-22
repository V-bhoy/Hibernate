package com.controller;


import com.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class HomeController {


    public static void main(String[] args) {
        Configuration configuration = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Employee e = new Employee();
        e.setEmployeeName("Ria Jones");
        e.setEmployeeAddress("Pune");
        e.setEmployeeEmail("ria@gmail.com");
        e.setEmployeePhone("1234567899");
        e.setEmployeeSalary(10000);
        e.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        e.setModifiedDate(Timestamp.valueOf(LocalDateTime.now()));

        session.save(e);
        transaction.commit();
        session.close();
        sessionFactory.close();

        System.out.println("Successfully saved Employee.");
    }

}
