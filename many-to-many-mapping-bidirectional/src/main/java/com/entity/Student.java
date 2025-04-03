package com.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="student_info")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
         @JoinTable(
                 name="student_course",
                 joinColumns=@JoinColumn(name="student_id", nullable=false),
                 inverseJoinColumns=@JoinColumn(name="course_id", nullable=false)
         )
    private List<Course> courses = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
      return toString(false);
    }

    public String toString(boolean showCourses){
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                (showCourses ? ", courses=" + courses : "") +
                '}';
    }
}
