package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
@Getter
@Setter
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "courses")
    @Column(name = "students")
    private Set<Student> students;

    public Course(){};
    public Course(int id, String instructor, String name) {
        this.id = id;
        this.instructor = instructor;
        this.name = name;
    }
    public Course(int id, String instructor, String name, Set<Student> students) {
        this.id = id;
        this.instructor = instructor;
        this.name = name;
        this.students = students;
    }
}
