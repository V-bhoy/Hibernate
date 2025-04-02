package com.repository;

import com.entity.Course;

import java.util.List;

public interface CourseRepo {
   void updateCourse(Course course, int courseId);
   Course getCourse(int courseId);
}
