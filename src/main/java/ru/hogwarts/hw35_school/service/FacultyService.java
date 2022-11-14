package ru.hogwarts.hw35_school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.hw35_school.model.Faculty;
import ru.hogwarts.hw35_school.model.Student;
import ru.hogwarts.hw35_school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> filterFacultyByNameOrColor(String nameOrColor) {
        return facultyRepository.findFacultyByNameOrColor(nameOrColor, nameOrColor).stream()
                .map(Faculty::toRecord)
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .map(ArrayList::new)
                .orElseThrow(() -> new RuntimeException("No faculty with this ID!"));
    }
}
