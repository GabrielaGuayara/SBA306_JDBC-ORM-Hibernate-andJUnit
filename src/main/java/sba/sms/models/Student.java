package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
import jakarta.persistence.*;
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "student")

public class Student {
    @Column(name = "email", unique = true, length = 50)
    @Id
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    private Set<Course> courses;

    public Student(){};
    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    public Student(Set<Course> courses, String email, String name, String password) {
        this.courses = courses;
        this.email = email;
        this.name = name;
        this.password = password;
    }

}



