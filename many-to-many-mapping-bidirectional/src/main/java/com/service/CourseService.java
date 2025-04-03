package com.service;

import com.entity.Course;
import com.entity.Student;

import java.util.List;
import java.util.Set;

public interface CourseService {
    void registerCourse(Course course);
    void registerStudent(Set<Integer> studentIds, int courseId);
    void updateCourse(Course course, int courseId);
    Course getCourse(int courseId);
    void deleteCourse(int courseId);
    void removeStudents(List<Student> students, int courseId);

}
