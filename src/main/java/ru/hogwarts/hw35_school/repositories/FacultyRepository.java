package ru.hogwarts.hw35_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hw35_school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);

    List<Faculty> findFacultyByNameOrColor(String name, String color);
}
