package model.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudentResponseDto(

        Long id,

        String fullName,

        String gender,

        LocalDate dateOfBirth

) {
    public String birthDate() {
        return dateOfBirth != null ? dateOfBirth.toString() : null;
    }
}