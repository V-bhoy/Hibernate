package com.service;

import com.entity.Course;
import com.repository.CourseRepo;

public class CourseServiceImpl implements CourseService {

    private CourseRepo courseRepo;
    public CourseServiceImpl(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
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
}
