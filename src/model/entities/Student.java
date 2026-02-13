package model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {
    private Integer id;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDateTime createdAt;

    public Student(String fullName, LocalDate birthDate, Gender gender){
        this.id = new Random().nextInt(999) + 1;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createdAt = LocalDateTime.now();
    }

    public enum Gender{
        MALE, FEMALE
    }
}