package com.service;

import com.entity.Course;

public interface CourseService {
    void updateCourse(Course course, int courseId);
    Course getCourse(int courseId);
}
