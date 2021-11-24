package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.Ad;
import maroqand.uz.real_estate.repository.AdRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdService {
    private final AdRepository fileRepository;


    public AdService(AdRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Ad save(Ad ad){
        return fileRepository.save(ad);
    }

    public Ad getOne(Long adId) {
        boolean exists = fileRepository.existsById(adId);
        if (!exists) {
            throw new IllegalStateException("Ad with this " + adId + " id does not exists");
        }
        return fileRepository.findById(adId).get();
    }

    public List<Ad> getAll() {
        return fileRepository.findAll();
    }
}
