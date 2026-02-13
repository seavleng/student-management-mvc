package model.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StudentRequestDto(
        String fullName,
        LocalDate birthDate,  // Changed from String to LocalDate
        String gender
) {
}