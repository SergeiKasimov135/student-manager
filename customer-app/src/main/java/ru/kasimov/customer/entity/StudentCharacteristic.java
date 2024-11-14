package ru.kasimov.customer.entity;

import java.util.UUID;

public record StudentCharacteristic(

        UUID id,

        int studentId,

        int rating,

        String characteristic
) {
}
