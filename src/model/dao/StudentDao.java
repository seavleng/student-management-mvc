package model.dao;

import model.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Student save(Student student);
    List<Student> getAll(int offset, int limit); // Pagination
    Optional<Student> findById(Integer id);
    Student update(Integer id, Student student);
    boolean removeById(Integer id);
}