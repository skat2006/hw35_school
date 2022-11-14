package ru.hogwarts.hw35_school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.hw35_school.exception.AvatarNotFoundException;
import ru.hogwarts.hw35_school.model.Avatar;
import ru.hogwarts.hw35_school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;

    @Value("${avatars.image.dir.path}")
    private String avatarsDir;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile multipartFile) throws IOException {
        byte[] data = multipartFile.getBytes();
        Avatar avatar = toRecord(multipartFile.getSize(), multipartFile.getContentType(), data);

        String extension = Optional.ofNullable(multipartFile.getOriginalFilename())
                .map(s -> s.substring(multipartFile.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        Path path = Paths.get(avatarsDir).resolve(avatar.getId() + extension);
        Files.write(path, data);
        avatar.setFilePath(path.toString());
        avatarRepository.save(avatar);
    }

    private Avatar toRecord(long size, String mediaType, byte[] data) {
        Avatar avatar = new Avatar();
        avatar.setFileSize(size);
        avatar.setMediaType(mediaType);
        avatar.setData(data);
        return avatarRepository.save(avatar);
    }

    public Pair<String, byte[]> readAvatarFromFile(Long id) throws IOException {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(AvatarNotFoundException::new);
        return Pair.of(avatar.getMediaType(), Files.readAllBytes(Paths.get(avatar.getFilePath())));
    }

    public Pair<String, byte[]> readAvatarFromDB(Long id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(AvatarNotFoundException::new);
        return Pair.of(avatar.getMediaType(), avatar.getData());
    }
}
