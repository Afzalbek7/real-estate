package maroqand.uz.real_estate.service;

import maroqand.uz.real_estate.domain.Ad;
import maroqand.uz.real_estate.repository.AdRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdService {
    private final AdRepository adRepository;


    public AdService(AdRepository fileRepository) {
        this.adRepository = fileRepository;
    }

    public Ad save(Ad ad) {
        ad.setCreateOn(new Date());
        return adRepository.save(ad);
    }

    public Ad getOne(Long adId) {
        boolean exists = adRepository.existsById(adId);
        if (!exists) {
            throw new IllegalStateException("Ad with this " + adId + " id does not exists");
        }
        return adRepository.findById(adId).get();
    }

    public List<Ad> getAll() {
        return adRepository.findAll();
    }

    public Ad update(Ad ad) {
        return adRepository.save(ad);
    }

    public void delete(Long adId) {
        boolean exists = adRepository.existsById(adId);
        if (!exists) {
            throw new IllegalStateException("Ad with this id - " + adId + " does not exists");
        }
        Ad ad = adRepository.findById(adId).get();
        ad.setDeleted(true);
        adRepository.save(ad);
    }
}
