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


public class StudentService implements StudentI {

    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public void createStudent(Student student) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            //Save student into the databases
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Student student = null;
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            student = session.get(Student.class, email);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        //Return the student by email
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Student WHERE email = :email AND password = :password";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            Student student = query.uniqueResult(); // Use uniqueResult() instead of getSingleResult()
            transaction.commit();
            return student != null;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Student"; // Adjusted to correctly refer to the entity
            Query<Student> query = session.createQuery(hql, Student.class);
            List<Student> students = query.getResultList();
            transaction.commit();
            return students;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "SELECT course FROM Course course JOIN course.students student WHERE student.email = :email";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("email", email);
            List<Course> studentCourses = query.getResultList();
            transaction.commit();
            return studentCourses;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            if (student != null && course != null) {
                student.getCourses().add(course);
                session.merge(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }
}