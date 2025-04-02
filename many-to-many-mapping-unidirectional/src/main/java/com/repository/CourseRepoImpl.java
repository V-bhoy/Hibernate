package com.repository;

import com.entity.Course;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CourseRepoImpl implements CourseRepo {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void updateCourse(Course course, int courseId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Course existingCourse = session.get(Course.class, courseId);
            if(existingCourse != null) {
                if(course.getTitle()!=null) existingCourse.setTitle(course.getTitle());
                session.merge(existingCourse);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Course getCourse(int courseId) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Course.class, courseId);
        }
    }
}
