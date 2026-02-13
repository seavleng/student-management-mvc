package model.service;

import model.dto.StudentRequestDto;
import model.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    StudentResponseDto createStudent(StudentRequestDto requestDto);
    List<StudentResponseDto> getAllStudents(int offset, int limit); // Pagination with offset and limit
    StudentResponseDto updateStudent(Integer id, StudentRequestDto requestDto);
    boolean removeStudentById(Integer id);
    StudentResponseDto getStudentById(Integer id);
}