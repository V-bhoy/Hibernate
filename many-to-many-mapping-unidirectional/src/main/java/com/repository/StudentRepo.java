package com.repository;

import com.entity.Course;
import com.entity.Student;

import java.util.List;

public interface StudentRepo {
    void registerStudent(Student student);
    void registerCourses(List<Course> courses, int studentId);
    void updateStudent(Student student, int studentId);
    void deleteStudent(int studentId);
    void removeCourses(List<Course> courses, int studentId);
    Student getStudentById(int studentId);
}
