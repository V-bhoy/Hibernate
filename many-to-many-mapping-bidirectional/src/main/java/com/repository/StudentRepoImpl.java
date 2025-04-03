package com.repository;

import com.entity.Course;
import com.entity.Student;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentRepoImpl implements StudentRepo {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void registerStudent(Student student) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void registerCourses(List<Course> courses, int studentId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Student existing = session.get(Student.class, studentId);
            if(existing != null) {
                List<Course> existingCourses = existing.getCourses();
                Iterator<Course> iterator = courses.iterator();
                while(iterator.hasNext()){
                    Course course = iterator.next();
                    if(course.getId()>0){
                        Course existingCourse = session.get(Course.class, course.getId());
                        if(existingCourse!=null){
                          if(!existingCourse.getStudents().contains(existing)){
                             existingCourses.add(existingCourse);
                          }
                        }
                    }else{
                        existingCourses.add(course);
                    }
                }
                session.merge(existing);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateStudent(Student student, int studentId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Student existing = session.get(Student.class, studentId);
            if(existing != null) {
                if(student.getName()!=null) existing.setName(student.getName());
                session.merge(existing);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Student existing = session.get(Student.class, studentId);
            if(existing != null) {
                List<Course> courses = existing.getCourses();
                for(Course course : courses){
                    course.getStudents().remove(existing); // removing the student from the list
                }
                existing.getCourses().clear();
                session.remove(existing);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeCourses(List<Course> courses, int studentId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Student existing = session.get(Student.class, studentId);
            if(existing != null) {
                List<Course> curr = existing.getCourses();
                Set<Integer> idsToRemove = courses.stream().map(Course::getId).collect(Collectors.toSet());
                Iterator<Course> iterator = curr.iterator();
                while(iterator.hasNext()){
                    Course course = iterator.next();
                    if(idsToRemove.contains(course.getId())){
                        iterator.remove();
                    }
                }
                session.merge(existing);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        try(Session session = sessionFactory.openSession()) {
            Student s = session.get(Student.class, studentId);
            if(s != null){
                Hibernate.initialize(s.getCourses());
            }
            return s;
        }
    }
}
