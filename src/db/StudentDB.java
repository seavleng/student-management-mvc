package db;

import model.entities.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDB {
    private final List<Student> studentList;

    public StudentDB(){
        studentList = new ArrayList<>();  // Start with empty list
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    // Add method to add a student
    public void addStudent(Student student) {
        studentList.add(student);
    }

    // Add method to remove a student by ID
    public boolean removeStudent(Integer id) {
        return studentList.removeIf(student -> student.getId().equals(id));
    }

    // Optional: Find student by ID
    public Student findStudentById(Integer id) {
        return studentList.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}