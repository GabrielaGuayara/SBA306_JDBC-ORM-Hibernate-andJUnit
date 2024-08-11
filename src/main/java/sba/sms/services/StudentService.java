package sba.sms.services;


import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */
@Log
public class StudentService implements StudentI {


    @Override
    public List<Student> getAllStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Student> students = null;
        try {
            transaction = session.beginTransaction();
            String hq1 = "FROM Student";
            Query<Student> query = session.createQuery(hq1, Student.class);
            students = query.getResultList();
            transaction.commit();
            log.info("Students retrieved successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve students", e);
        }
        return students;
    }

    @Override
    public void createStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
            log.info("Students retrieved successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error creating students: " + e.getMessage());
            throw new RuntimeException("Failed to create students", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Student student = null;
        try {
            transaction = session.beginTransaction();
            String hq2 = "FROM Student WHERE email = :email";
            Query<Student> query = session.createQuery(hq2, Student.class);
            student = query.uniqueResult();
            transaction.commit();
            log.info("Students retrieved successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error retrieving students: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve students", e);
        } finally {
            session.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        boolean isValid = false;
        try {
            transaction = session.beginTransaction();
            String hq3 = "FROM Student WHERE email = :email AND password = :password";
            Query<Student> query = session.createQuery(hq3, Student.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            transaction.commit();
            log.info("Student validation completed");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error validating student: " + e.getMessage());
            throw new RuntimeException("Failed to validate student", e);
        } finally {
            session.close();
        }
        return isValid;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hq4 = "FROM Student WHERE email = :email";
            Student student = session.createQuery(hq4, Student.class).setParameter("email", email).uniqueResult();
            Course course = session.get(Course.class, courseId);
            if (student != null && course != null) {
                student.getCourses().add(course);
                session.update(student);
                transaction.commit();
                log.info("Student registered to course successfully");
            } else {
                log.warning("Student or Course not found");

            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error registering student to course: " + e.getMessage());
            throw new RuntimeException("Failed to register student to course", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Course> courses = null;
        try {
            transaction = session.beginTransaction();
            String hq4 = "FROM Student WHERE email = :email";
            Student student = session.createQuery(hq4, Student.class).setParameter("email", email).uniqueResult();
            if (student != null) {
                courses = new ArrayList<>(student.getCourses());
            } else {
                log.warning("Student not found for email" + email);

            }
            transaction.commit();
            log.info("Courses for student retrieved successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            log.severe("Error retrieving student courses " + e.getMessage());
            throw new RuntimeException("Failed to retrieve student courses", e);
        } finally {
            session.close();
        }
        return courses;
    }
}
