package model.dao;

import java.util.List;

public interface StudentDao {

    void create(Student student);
    List<Student> getAll();

}
