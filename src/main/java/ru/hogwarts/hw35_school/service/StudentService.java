package ru.hogwarts.hw35_school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.hw35_school.exception.AvatarNotFoundException;
import ru.hogwarts.hw35_school.exception.StudentNotFoundException;
import ru.hogwarts.hw35_school.model.Avatar;
import ru.hogwarts.hw35_school.model.Faculty;
import ru.hogwarts.hw35_school.model.Student;
import ru.hogwarts.hw35_school.repositories.AvatarRepository;
import ru.hogwarts.hw35_school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public StudentService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax).stream()
                .map(Student::toRecord)
                .collect(Collectors.toList());
    }

    public Faculty getFacultyById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(Student::getFaculty).orElseThrow(() -> new RuntimeException("No student with this ID!"));
    }

    public Student patchStudentAvatar(Long id, Long avatarId) {
        Optional<Student> tempStudent = studentRepository.findById(id);
        Optional<Avatar> tempAvatar = avatarRepository.findById(avatarId);
        if (tempAvatar.isEmpty()) throw new AvatarNotFoundException();
        if (tempStudent.isEmpty()) throw new StudentNotFoundException();

        Student student = tempStudent.get();
        student.setAvatar(tempAvatar.get());

        return Student.toRecord(studentRepository.save(student));
    }
}
