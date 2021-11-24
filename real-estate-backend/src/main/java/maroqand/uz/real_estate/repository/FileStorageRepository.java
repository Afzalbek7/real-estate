package maroqand.uz.real_estate.repository;

import maroqand.uz.real_estate.domain.FileStorage;
import maroqand.uz.real_estate.domain.FileStorageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    FileStorage findByHashId(String hashId);

    List<FileStorage> findAllByFileStorageStatus(FileStorageStatus fileStorageStatus);
}
