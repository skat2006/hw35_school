package ru.hogwarts.hw35_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hw35_school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByStudentId(Long studentId);
}
