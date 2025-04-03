package com.service;

import com.entity.Course;
import com.entity.Student;
import com.repository.CourseRepo;

import java.util.List;
import java.util.Set;

public class CourseServiceImpl implements CourseService {

    private CourseRepo courseRepo;
    public CourseServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public void registerCourse(Course course) {
        try{
            courseRepo.registerCourse(course);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in course service handler: ",e);
        }
    }

    @Override
    public void registerStudent(Set<Integer> studentIds, int courseId) {
        try{
            courseRepo.registerStudent(studentIds, courseId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in course service handler: ",e);
        }
    }

    @Override
    public void updateCourse(Course course, int courseId) {
        try{
            courseRepo.updateCourse(course, courseId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in course service handler: ",e);
        }
    }

    @Override
    public Course getCourse(int courseId) {
        return courseRepo.getCourse(courseId);
    }

    @Override
    public void deleteCourse(int courseId) {
        try{
            courseRepo.deleteCourse(courseId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in course service handler: ",e);
        }
    }

    @Override
    public void removeStudents(List<Student> students, int courseId) {
        try{
            courseRepo.removeStudents(students, courseId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in course service handler: ",e);
        }
    }
}