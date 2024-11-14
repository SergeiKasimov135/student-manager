package ru.kasimov.feedback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("student_characteristic")
public class StudentCharacteristic {

    @Id
    private UUID id;

    private int studentId;

    private int rating;

    private String characteristic;

}
