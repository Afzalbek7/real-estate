package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.Announcement;
import maroqand.uz.real_estate.domain.FileStorage;
import maroqand.uz.real_estate.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final FileStorageService fileStorageService;

    public AnnouncementService(AnnouncementRepository adRepository, FileStorageService fileStorageService) {
        this.announcementRepository = adRepository;
        this.fileStorageService = fileStorageService;
    }

    public Announcement create(Announcement announcement, Long imageId) {
        announcement.setCreateOn(new Date());
//        List<FileStorage> fileStorageList =new ArrayList<>();
        FileStorage fileStorage = fileStorageService.getFS(imageId);
//        fileStorageList.add(fileStorage);
        announcement.getFileStorageList().add(fileStorage);
        return announcementRepository.save(announcement);
    }

    public Announcement getOne(Long adId) {
        boolean exists = announcementRepository.existsById(adId);
        if (!exists) {
            throw new IllegalStateException("Ad with this id - " + adId + " does not exists");
        }
        Announcement announcement = announcementRepository.findById(adId).get();
        return announcement;
    }

    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }


    public Announcement update(Announcement ad) {
        return announcementRepository.save(ad);
    }

    public void delete(Long adId) {
        boolean exists = announcementRepository.existsById(adId);
        if (!exists) {
            throw new IllegalStateException("Ad with this id " + adId + " does not exists");
        }
        announcementRepository.deleteById(adId);
    }
}
