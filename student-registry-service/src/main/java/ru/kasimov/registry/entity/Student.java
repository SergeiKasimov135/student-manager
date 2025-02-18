package ru.kasimov.registry.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(schema = "registry", name = "t_student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries(
        @NamedQuery(
                name="Student.findAllByNameLikeIgnoringCase",
                query = "select s from Student s where s.name ilike :filter"
        )
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_name")
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @Column(name = "c_last_name")
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @Column(name = "c_age")
    @Range(min = 17, max = 45)
    private Integer age;

    @Column(name = "c_email")
    @Email
    private String email;

    @Column(name = "c_phone_number")
    @NotNull
    @Pattern(regexp = "\\d{11}")
    private String phoneNumber;

    @Column(name = "c_course_level")
    @Range(min = 1, max = 4)
    private Integer courseLevel;

}
