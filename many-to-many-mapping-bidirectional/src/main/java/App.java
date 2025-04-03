import com.entity.Course;
import com.entity.Student;
import com.repository.CourseRepoImpl;
import com.repository.StudentRepoImpl;
import com.service.CourseService;
import com.service.CourseServiceImpl;
import com.service.StudentService;
import com.service.StudentServiceImpl;
import com.util.HibernateUtil;
import com.util.InputUtil;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run(){
        System.out.println("WELCOME TO THE N-N MAPPING UNIDIRECTIONAL DEMO!");
        StudentService ss = new StudentServiceImpl(new StudentRepoImpl());
        CourseService cs = new CourseServiceImpl(new CourseRepoImpl());
        try (Scanner sc = new Scanner(System.in)) {
            do {
                int choice = InputUtil.menuOptions(sc);
                switch (choice) {
                    case 1: {
                        Student s = InputUtil.acceptStudentDetails(sc);
                        ss.registerStudent(s);
                        System.out.println("Person added successfully!");
                    }
                    break;
                    case 2: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            Student updatedStudent = InputUtil.acceptStudentDetails(sc);
                            ss.updateStudent(updatedStudent, studentId);
                            System.out.println("Student updated successfully!");
                        } else {
                            System.out.println("Student not found!");
                        }
                    }
                    break;
                    case 3: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            ss.deleteStudent(studentId);
                            System.out.println("Student deleted successfully!");
                        } else {
                            System.out.println("Student not found!");
                        }
                    }
                    break;
                    case 4: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            List<Course> courses = InputUtil.acceptCoursesToRegister(sc);
                            if (courses.isEmpty()) {
                                System.out.println("No courses found!");
                            } else {
                                ss.registerCourses(courses, studentId);
                                System.out.println("Courses added successfully!");
                            }
                        } else {
                            System.out.println("No student found!");
                        }
                    }
                    break;
                    case 5: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            Course updateCourse = InputUtil.acceptCourseDetails(sc);
                            cs.updateCourse(updateCourse, courseId);
                            System.out.println("Course updated successfully!");
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 6: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            List<Course> courses = s.getCourses();
                            if (courses.isEmpty()) {
                                System.out.println("No courses found!");
                            } else {
                                List<Course> deleteCourses = InputUtil.acceptCoursesToDelete(sc, courses);
                                if (deleteCourses.isEmpty()) {
                                    System.out.println("No courses found to delete!");
                                } else {
                                    ss.removeCourses(deleteCourses, studentId);
                                    System.out.println("Courses removed successfully!");
                                }
                            }
                        } else {
                            System.out.println("No student found!");
                        }
                    }
                    break;
                    case 7: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            System.out.println(s.getCourses());
                        } else {
                            System.out.println("No student found!");
                        }
                    }
                    break;
                    case 8: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            System.out.println(s);
                        } else {
                            System.out.println("No student found!");
                        }
                    }
                    break;
                    case 9: {
                        int studentId = InputUtil.acceptStudentId(sc);
                        Student s = ss.getStudentById(studentId);
                        if (s != null) {
                            System.out.println(s.toString(true));
                        } else {
                            System.out.println("No student found!");
                        }
                    }
                    break;
                    case 10: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            System.out.println(c);
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 11: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            System.out.println(c.getStudents());
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 12: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            System.out.println(c.toString(true));
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 13: {
                        Course c = InputUtil.acceptCourseDetails(sc);
                        cs.registerCourse(c);
                        System.out.println("Course registered successfully!");
                    }
                    break;
                    case 14: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                           Set<Integer> studentIds = InputUtil.acceptStudentIds(sc);
                           cs.registerStudent(studentIds, courseId);
                           System.out.println("Students registered successfully!");
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 15: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            List<Student> students = c.getStudents();
                            List<Student> studentsToDelete = InputUtil.acceptStudentsToDelete(sc, students);
                            cs.removeStudents(studentsToDelete, courseId);
                            System.out.println("Students removed successfully!");
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    case 16: {
                        int courseId = InputUtil.acceptCourseId(sc);
                        Course c = cs.getCourse(courseId);
                        if (c != null) {
                            cs.deleteCourse(courseId);
                            System.out.println("Course deleted successfully!");
                        } else {
                            System.out.println("No course found!");
                        }
                    }
                    break;
                    default:
                        System.out.println("INVALID CHOICE!");
                }
            } while (InputUtil.wantToContinue(sc));
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        HibernateUtil.shutdown();
        System.out.println("THANK YOU!");
    }
}
