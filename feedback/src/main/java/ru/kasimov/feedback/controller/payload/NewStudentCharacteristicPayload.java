package ru.kasimov.feedback.controller.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewStudentCharacteristicPayload(
        @NotNull(message = "{feedback.students.characteristics.create.errors.student_id_is_null}")
        Integer studentId,

        @NotNull(message = "{feedback.students.characteristics.create.errors.rating_is_null}")
        @Min(value = 1, message = "{feedback.students.characteristics.create.errors.rating_is_below_min}")
        @Max(value = 5, message = "{feedback.students.characteristics.create.errors.rating_is_above_max}")
        Integer rating,

        @Size(max = 1000, message = "{feedback.students.characteristics.create.errors.characteristic_is_too_big}")
        String characteristic
) {
}
