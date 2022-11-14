package ru.hogwarts.hw35_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hw35_school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);
}
