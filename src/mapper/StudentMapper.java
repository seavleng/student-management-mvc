package mapper;

import model.dto.StudentRequestDto;
import model.dto.StudentResponseDto;
import model.entities.Student;

public class StudentMapper {

    public Student fromStudentRequestDto(StudentRequestDto requestDto) {
        return new Student(
                requestDto.fullName(),
                requestDto.birthDate(),  // Already a LocalDate
                Student.Gender.valueOf(requestDto.gender().toUpperCase())
        );
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        return StudentResponseDto.builder()
                .id(Long.valueOf(student.getId()))
                .fullName(student.getFullName())
                .gender(student.getGender().toString())
                .dateOfBirth(student.getBirthDate())
                .build();
    }
}