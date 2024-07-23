package ru.kasimov.manager.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String name;

    private String lastName;

    private Integer age;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.ORDINAL)
    private CourseLevel courseLevel;

    public Student(String name, String lastName, Integer age, String email, String phoneNumber, CourseLevel courseLevel) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.courseLevel = courseLevel;
    }
}
