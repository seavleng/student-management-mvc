package model.dao;

import db.StudentDB;
import model.entities.Student;

import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao{

    private final StudentDB studentDb;

    public StudentDaoImpl(StudentDB studentDb) {
        this.studentDb = studentDb;
    }

    @Override
    public Student save(Student student) {
        studentDb.getStudentList().add(student);
        return student;
    }

    @Override
    public List<Student> getAll(int offset, int limit) {
        // Pagination implementation
        List<Student> allStudents = studentDb.getStudentList();
        int start = Math.min(offset, allStudents.size());
        int end = Math.min(offset + limit, allStudents.size());

        if (start >= allStudents.size()) {
            return List.of(); // Return empty list if offset is beyond list size
        }

        return allStudents.subList(start, end);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentDb.getStudentList()
                .stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    @Override
    public Student update(Integer id, Student student) {
        Optional<Student> existingStudent = findById(id);
        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();
            studentToUpdate.setFullName(student.getFullName());
            studentToUpdate.setBirthDate(student.getBirthDate());
            studentToUpdate.setGender(student.getGender());
            return studentToUpdate;
        }
        return null;
    }

    @Override
    public boolean removeById(Integer id) {
        return studentDb.getStudentList().removeIf(student -> student.getId().equals(id));
    }
}