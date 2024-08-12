package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    Session session = null;

    public void createCourse(Course course) {
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Utilize persist to save course in our database
            session.persist(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            session.close();
        }
    }


    public Course getCourseById(int courseId) {
        Transaction transaction = null;
        Course course = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            course = session.get(Course.class, courseId);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            session.close();
        }

        return course;
    }

    public List<Course> getAllCourses() {
        Transaction transaction = null;
        List<Course> courses = new ArrayList<>();
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            String hql = "SELECT course FROM Course course";
            TypedQuery<Course> query = session.createQuery(hql, Course.class);

           courses = query.getResultList();
            // Returning a list of courses
            return courses;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }finally {
            session.close();
        }

    }

    /**
     * @return
     */
    @Override
    public Object getId() {
        return null;
    }

}