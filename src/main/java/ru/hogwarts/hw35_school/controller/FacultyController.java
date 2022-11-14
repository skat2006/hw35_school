package ru.hogwarts.hw35_school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.hw35_school.model.Faculty;
import ru.hogwarts.hw35_school.model.Student;
import ru.hogwarts.hw35_school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "color")
    public Collection<Faculty> filterFacultyByColor(@RequestParam String color) {
        return facultyService.filterFacultyByColor(color);
    }

    @GetMapping(params = "nameOrColor")
    public Collection<Faculty> filterFacultyByNameOrColor(@RequestParam String nameOrColor) {
        return facultyService.filterFacultyByNameOrColor(nameOrColor);
    }

    @GetMapping("/{id}/getStudents")
    public Optional<List<Student>> getStudentsByFaculty(@PathVariable Long id) {
        return Optional.ofNullable(facultyService.getStudentsByFaculty(id));
    }


}
