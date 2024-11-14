package ru.kasimov.customer.entity;

public record Student(int id, String name, String lastName, int age,
                      String email, String phoneNumber, int courseLevel) {
}
