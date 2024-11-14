package ru.kasimov.customer.client.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewStudentCharacteristicPayload(
        Integer studentId,

        Integer rating,

        String characteristic
) {
}
