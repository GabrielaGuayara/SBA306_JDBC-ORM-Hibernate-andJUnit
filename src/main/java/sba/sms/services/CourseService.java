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

    private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();

        @Override
        public void createCourse(Course course) {
            Transaction transaction = null;

            try (Session session = factory.openSession()) {
                transaction = session.beginTransaction();
                session.persist(course);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println(e.getMessage());
            }
        }

        @Override
        public Course getCourseById(int courseId) {
            Transaction transaction = null;
            Course course = null;

            try (Session session = factory.openSession()) {
                transaction = session.beginTransaction();
                course = session.get(Course.class, courseId);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println(e.getMessage());
            }

            return course;
        }

        @Override
        public List<Course> getAllCourses() {
            List<Course> courses = new ArrayList<>();

            try (Session session = factory.openSession()) {
                Transaction transaction = session.beginTransaction();
                String hql = "FROM Course";
                TypedQuery<Course> query = session.createQuery(hql, Course.class);
                courses = query.getResultList();
                transaction.commit();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return courses;
        }

        @Override
        public Object getId() {
            return null;
        }
    }

