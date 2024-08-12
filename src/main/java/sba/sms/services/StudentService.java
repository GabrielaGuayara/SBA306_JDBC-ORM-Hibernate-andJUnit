package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;


import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = null;

    public void createStudent(Student newStudent) {
        Transaction transaction = null;

        try {

            //Opening session and beginning the transaction
            session = factory.openSession();
            transaction = session.beginTransaction();

            session.persist(newStudent);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    public Student getStudentByEmail(String email) {

        Student student = null;
        Transaction transaction = null;
        try {

            session = factory.openSession();
            transaction = session.beginTransaction();
            student = session.get(Student.class, email);

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }
        return student;
    }

    public boolean validateStudent(String email, String password) {

        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();


            String hql = "FROM  Student WHERE email = :email AND password = :password";
            Query<Student> query = session.createQuery(hql, Student.class);

            query.setParameter("email", email);
            query.setParameter("password", password);
            Student student = query.getSingleResult();
            transaction.commit();

            return student != null;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
        }

        return false;

    }

    public List<Student> getAllStudents() {
        Transaction transaction = null;

        try {
            String hqlString = "SELECT Student from Students";
            Query<Student> query = session.createQuery(hqlString, Student.class);
            return query.getResultList();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e);
            }
            System.out.println(e);
            return null;
        }
    }

    public List<Course> getStudentCourses(String email) {
        Transaction transaction = null;
        try {

            transaction = session.beginTransaction();

            //String to query all te courses for the students
            String hql = "SELECT course FROM Course course JOIN course.students student WHERE student.email = :email";

            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("email", email);
            List<Course> StudentCourses = query.getResultList();
            transaction.commit();
            return StudentCourses;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println(e.getMessage());
            }
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            student.getCourses().add(course);
            session.merge(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

}