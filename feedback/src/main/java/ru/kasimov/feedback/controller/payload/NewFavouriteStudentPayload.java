package ru.kasimov.feedback.controller.payload;

import jakarta.validation.constraints.NotNull;

public record NewFavouriteStudentPayload(
        @NotNull(message = "{feedback.students.favourites.create.errors.student_id_is_null}")
        Integer studentId
) {
}
