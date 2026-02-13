package model.service;

import exception.StudentException;
import mapper.StudentMapper;
import model.dao.StudentDao;
import model.dto.StudentRequestDto;
import model.dto.StudentResponseDto;
import model.entities.Student;

import java.time.LocalDate;
import java.util.List;

public class StudentServiceImpl implements StudentService{
    private final StudentDao studentDao;
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentDao studentDao, StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        try {
            // Business logic validation: Student must be at least 4 years old
            if (requestDto.birthDate().isAfter(LocalDate.now().minusYears(4))){
                throw new StudentException("Student must be at least 4 years old");
            }

            // Business logic validation: Birth date cannot be in the future
            if (requestDto.birthDate().isAfter(LocalDate.now())) {
                throw new StudentException("Birth date cannot be in the future");
            }

            Student student = studentMapper.fromStudentRequestDto(requestDto);
            Student savedStudent = studentDao.save(student);
            return studentMapper.toStudentResponseDto(savedStudent);

        } catch (StudentException e) {
            throw e;
        } catch (Exception e) {
            throw new StudentException("Failed to create student: " + e.getMessage(), e);
        }
    }

    @Override
    public List<StudentResponseDto> getAllStudents(int offset, int limit) {
        try {
            // Business logic validation: offset and limit must be non-negative
            if (offset < 0) {
                throw new StudentException("Offset must be non-negative");
            }
            if (limit <= 0) {
                throw new StudentException("Limit must be positive");
            }
            if (limit > 100) {
                throw new StudentException("Limit cannot exceed 100 records");
            }

            return studentDao.getAll(offset, limit)
                    .stream()
                    .map(studentMapper::toStudentResponseDto)
                    .toList();

        } catch (StudentException e) {
            throw e;
        } catch (Exception e) {
            throw new StudentException("Failed to retrieve students: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentResponseDto updateStudent(Integer id, StudentRequestDto requestDto) {
        try {
            // Business logic validation: Check if student exists
            Student existingStudent = studentDao.findById(id)
                    .orElseThrow(() -> new StudentException("Student with ID " + id + " not found"));

            // Business logic validation: Student must be at least 4 years old
            if (requestDto.birthDate().isAfter(LocalDate.now().minusYears(4))){
                throw new StudentException("Student must be at least 4 years old");
            }

            // Business logic validation: Birth date cannot be in the future
            if (requestDto.birthDate().isAfter(LocalDate.now())) {
                throw new StudentException("Birth date cannot be in the future");
            }

            Student studentToUpdate = studentMapper.fromStudentRequestDto(requestDto);
            Student updatedStudent = studentDao.update(id, studentToUpdate);

            if (updatedStudent == null) {
                throw new StudentException("Failed to update student with ID " + id);
            }

            return studentMapper.toStudentResponseDto(updatedStudent);

        } catch (StudentException e) {
            throw e;
        } catch (Exception e) {
            throw new StudentException("Failed to update student: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean removeStudentById(Integer id) {
        try {
            // Business logic validation: Check if student exists before removing
            if (studentDao.findById(id).isEmpty()) {
                throw new StudentException("Student with ID " + id + " not found");
            }

            boolean removed = studentDao.removeById(id);
            if (!removed) {
                throw new StudentException("Failed to remove student with ID " + id);
            }

            return true;

        } catch (StudentException e) {
            throw e;
        } catch (Exception e) {
            throw new StudentException("Failed to remove student: " + e.getMessage(), e);
        }
    }

    @Override
    public StudentResponseDto getStudentById(Integer id) {
        try {
            Student student = studentDao.findById(id)
                    .orElseThrow(() -> new StudentException("Student with ID " + id + " not found"));

            return studentMapper.toStudentResponseDto(student);

        } catch (StudentException e) {
            throw e;
        } catch (Exception e) {
            throw new StudentException("Failed to retrieve student: " + e.getMessage(), e);
        }
    }
}