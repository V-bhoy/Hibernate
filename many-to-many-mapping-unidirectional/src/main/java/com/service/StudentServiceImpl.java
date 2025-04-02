package com.service;

import com.entity.Course;
import com.entity.Student;
import com.repository.StudentRepo;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
    @Override
    public void registerStudent(Student student) {
        try{
            studentRepo.registerStudent(student);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in student service handler: ",e);
        }
    }

    @Override
    public void registerCourses(List<Course> courses, int studentId) {
        try{
            studentRepo.registerCourses(courses, studentId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in student service handler: ",e);
        }
    }

    @Override
    public void updateStudent(Student student, int studentId) {
        try{
            studentRepo.updateStudent(student, studentId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in student service handler: ",e);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try{
            studentRepo.deleteStudent(studentId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in student service handler: ",e);
        }
    }

    @Override
    public void removeCourses(List<Course> courses, int studentId) {
        try{
            studentRepo.removeCourses(courses, studentId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in student service handler: ",e);
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepo.getStudentById(studentId);
    }
}
