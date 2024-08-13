package sba.sms.services;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.List;
import static org.junit.Assert.*;

class StudentServiceTest {

    private static Session session;

    @BeforeAll
    static void setUp() {
       session = HibernateUtil.getSessionFactory().openSession();
    }

    //Test case to check if the student is created successfully
    @Test
    public void testCreateStudent() {
        session.beginTransaction();
        StudentService student = new StudentService();
        Student student1 = new Student("anna@gmail.com", "anna alvarez", "password");

        student.createStudent(student1);
        session.persist(student1);
        Student getStudent = student.getStudentByEmail(student1.getEmail());
        assertNotNull(getStudent );
        assertEquals("anna@gmail.com", getStudent.getEmail());
    }

    //Test case to check if the course is created successfully
    @Test
    public void testCreateCourse() {
        session.beginTransaction();
        CourseService course = new CourseService();
        Course course1 = new Course("Math", "Alvarado");

        course.createCourse(course1);

        Course retrievedCourse = course.getCourseById(course1.getId());
        assertNotNull(retrievedCourse);
        assertEquals("Math", retrievedCourse.getName());
    }

    //Test case to check if all the courses are created successfully
    @Test
    public void testGetAllCourses() {
        session.beginTransaction();
        CourseService courseService = new CourseService();
        courseService.createCourse(new Course("Chemistry", "Alvarez"));
        courseService.createCourse(new Course("History", "Gonzalez"));

        List<Course> courses = courseService.getAllCourses();
        session.persist(courses);
        assertNotNull(courses);
        assertEquals(2, courses.size());
    }

    //Test case to check if a course can be retrieved by id
    @Test
    public void testGetCourseById() {
        session.beginTransaction();
        CourseService courseService = new CourseService();
        Course course = new Course("Physics", "Jimenez");

        courseService.createCourse(course);
        session.persist(course);

        Course retrievedCourse = courseService.getCourseById(course.getId());
        assertNotNull(retrievedCourse);
        assertEquals("Physics", retrievedCourse.getName());
    }


    @AfterAll
    static void CloseUp() {
        session.close();
    }

}