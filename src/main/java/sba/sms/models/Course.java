package sba.sms.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "course")
public class Course {
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;


    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "instructor", length = 50, nullable = false)
    private String instructor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.PERSIST })
    @JoinTable(name = "students_courses", joinColumns = @JoinColumn(name = "courses_id"), inverseJoinColumns = @JoinColumn(name = "student_email"))
    private Set<Student> students = new HashSet<>();


    //No arguments constructor
    public Course(){}

    //All arguments constructor
    public Course(String name, String instructor, Set<Student> students) {
        this.name = name;
        this.instructor = instructor;
        this.students = students;
    }

    //Required args constructor
    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    public int getId() {
        return id;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

}
