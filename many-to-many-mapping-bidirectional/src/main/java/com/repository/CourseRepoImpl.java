package com.repository;

import com.entity.Course;
import com.entity.Student;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CourseRepoImpl implements CourseRepo {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void registerCourse(Course course) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void registerStudent(Set<Integer> studentIds, int courseId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            if(course != null){
                List<Student> existingStudents = course.getStudents();
               for(Integer studentId : studentIds){
                   Student managedStudent = session.get(Student.class, studentId);
                   if(managedStudent != null && !existingStudents.contains(managedStudent)){
                       managedStudent.getCourses().add(course);
                       session.merge(managedStudent);
                   }
               }
            }
            tx.commit();
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

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
    public void deleteCourse(int courseId) {
       Transaction tx = null;
       try(Session session = sessionFactory.openSession()){
           tx = session.beginTransaction();
           Course existingCourse = session.get(Course.class, courseId);
           if(existingCourse != null) {
               List<Student> students = existingCourse.getStudents();
               for(Student student : students){
                   student.getCourses().remove(existingCourse);
               }
               session.remove(existingCourse);
           }
           tx.commit();
       }catch(Exception e) {
           if(tx != null) tx.rollback();
           throw e;
       }
    }

    @Override
    public void removeStudents(List<Student> students, int courseId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            Course existingCourse = session.get(Course.class, courseId);
            if(existingCourse != null) {
                for(Student student : students){
                    Student managedStudent = session.get(Student.class, student.getId());
                    if(managedStudent != null){
                        managedStudent.getCourses().removeIf(course->course.getId()==courseId);
                        session.merge(managedStudent);
                    }
                }
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
            Course c = session.get(Course.class, courseId);
            if(c != null){
                Hibernate.initialize(c.getStudents());
            };
            return c;
        }
    }
}