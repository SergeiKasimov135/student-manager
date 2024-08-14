package ru.kasimov.manager.controller.payload;

public record EditStudentPayload(String name, String lastName, Integer age,
                                 String email, String phoneNumber, Integer courseLevel) {
}
