package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.Ad;
import maroqand.uz.real_estate.domain.FileStorage;
import maroqand.uz.real_estate.domain.FileStorageStatus;
import maroqand.uz.real_estate.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {
    private final FileStorageRepository fileStorageRepository;
    private final AdService adService;

    @Value("${upload.folder}")
    private String uploadFolder;

    private final Hashids hashids;

    public FileStorageService(FileStorageRepository fileStorageRepository, AdService adService) {
        this.fileStorageRepository = fileStorageRepository;
        this.adService = adService;
        this.uploadFolder = uploadFolder;
        this.hashids = new Hashids(getClass().getName(), 6);
    }



    public Long save(MultipartFile multipartFile, boolean imageType, Ad ad) {
        Long fileStorageId;
        adService.save(ad);
        FileStorage fileStorage = new FileStorage();
        fileStorage.setAd(ad);
        if (imageType == true) {
            fileStorage.setMainImage(true);
        }
        else {
            fileStorage.setMainImage(false);
        }

        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);

        fileStorageRepository.save(fileStorage);

        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate()));
        if (!uploadFolder.exists() & uploadFolder.mkdirs()) {
            System.out.println("papka yaratildi");
        }
        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s", 1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                fileStorage.getHashId(),
                fileStorage.getExtension()));

        fileStorageRepository.save(fileStorage);

        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(),
                fileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileStorageId = fileStorage.getId();

        return fileStorageId;

    }




    public FileStorage getFS(Long imageId) {
        boolean exists = fileStorageRepository.existsById(imageId);
        if (!exists) {
            throw new IllegalStateException("The image with this id does not exists");
        }
        FileStorage fileStorage = fileStorageRepository.findById(imageId).get();
        return fileStorage;
    }


    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId) {
        return fileStorageRepository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        FileStorage fileStorage = findByHashId(hashId);
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorage.getUploadPath()));
        if (file.delete()) {
            fileStorageRepository.delete(fileStorage);
        }
    }

    public List<FileStorage> getAll() {
        return fileStorageRepository.findAll();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllDraft() {
        List<FileStorage> fileStorageList = fileStorageRepository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorageList.forEach(fileStorage -> {
            delete(fileStorage.getHashId());
        });
    }

    private String getExt(String fileName) {
        String ext = null;

        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }


//    public Long save(MultipartFile multipartFile) {
//        FileStorage fileStorage = new FileStorage();
//        fileStorage.setName(multipartFile.getOriginalFilename());
//        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
//        fileStorage.setFileSize(multipartFile.getSize());
//        fileStorage.setContentType(multipartFile.getContentType());
//        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
//
//        fileStorageRepository.save(fileStorage);
//
//        Date now = new Date();
//        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
//                1900 + now.getYear(),
//                1 + now.getMonth(),
//                now.getDate()));
//        if (!uploadFolder.exists() & uploadFolder.mkdirs()) {
//            System.out.println("papka yaratildi");
//        }
//        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
//        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s", 1900 + now.getYear(),
//                1 + now.getMonth(),
//                now.getDate(),
//                fileStorage.getHashId(),
//                fileStorage.getExtension()));
//
//        fileStorageRepository.save(fileStorage);
//
//        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(),
//                fileStorage.getExtension()));
//        try {
//            multipartFile.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileStorage.getId();
//    }

}
