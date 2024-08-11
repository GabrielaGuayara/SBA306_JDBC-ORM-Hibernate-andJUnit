package sba.sms.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {
    private EntityManager entityManager;

    public CourseService(){}
    public CourseService(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void createCourse(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(course);
            transaction.commit();
            System.out.println("Course created successfully");
        }catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            System.out.println("Error creating course: " + e.getMessage());
            throw new RuntimeException("Failed to create course", e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return List.of();
    }

    @Override
    public Course getCourseById(int courseId) {
        return null;
    }
}
