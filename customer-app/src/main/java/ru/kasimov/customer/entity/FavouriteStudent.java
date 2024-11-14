package ru.kasimov.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record FavouriteStudent(
        UUID id,

        int studentId
) {
}
