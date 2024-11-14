package ru.kasimov.customer.controller.payload;

public record NewStudentCharacteristicPayload(
        Integer rating,

        String characteristic
) {
}
