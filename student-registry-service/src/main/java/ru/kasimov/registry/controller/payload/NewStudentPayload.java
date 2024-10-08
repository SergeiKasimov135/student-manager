package ru.kasimov.registry.controller.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record NewStudentPayload(
        @NotNull(message = "{students.create.errors.name_is_null}")
        @Size(min = 2, max = 20, message = "{students.create.errors.name_size_is_invalid}")
        String name,

        @NotNull(message = "{students.create.errors.lastName_is_null}")
        @Size(min = 2, max = 20, message = "{students.create.errors.lastName_size_is_invalid}")
        String lastName,

        @Range(min = 17, max = 45, message = "{students.create.errors.age_is_invalid}")
        Integer age,

        @Email(message = "{students.create.errors.email_is_invalid}")
        String email,

        @NotNull(message = "{students.create.errors.phoneNumber_is_null}")
        @Pattern(regexp = "\\d{11}", message = "{students.create.errors.phoneNumber_is_invalid}")
        String phoneNumber,

        @Range(min = 1, max = 4, message = "{students.create.errors.courseLevel_is_invalid}")
        Integer courseLevel
) {
}