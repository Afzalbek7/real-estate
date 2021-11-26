package maroqand.uz.real_estate.web.rest;

import maroqand.uz.real_estate.domain.Ad;
import maroqand.uz.real_estate.service.AdService;
import maroqand.uz.real_estate.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;


import java.io.IOException;

@RestController()
@RequestMapping("/api")
public class FileController {
    private final AdService adService;
    private final FileStorageService fileStorageService;

    @Value("${upload.folder}")
    private String uploadFolder;


    public FileController(AdService adService, FileStorageService fileStorageService) {
        this.adService = adService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Ad> createFile(@ModelAttribute Ad ad, RedirectAttributes ra,
                                         @RequestParam("primaryImage") MultipartFile mainMultipartFile,
                                         @RequestParam("extraImage") MultipartFile[] extraMultipartFiles) throws IOException {

        fileStorageService.save(mainMultipartFile, true, ad);
        String mainImageName = StringUtils.cleanPath(mainMultipartFile.getOriginalFilename());
        ad.setMainImage(mainImageName);
        int count = 0;
        for (MultipartFile extraMultipartFile : extraMultipartFiles) {
            fileStorageService.save(extraMultipartFile, false, ad);
            String extraImageName = StringUtils.cleanPath(extraMultipartFile.getOriginalFilename());

            if (count == 0) ad.setExtraImage1(extraImageName);
            if (count == 1) ad.setExtraImage2(extraImageName);
            if (count == 2) ad.setExtraImage3(extraImageName);

            count++;
        }
        Ad saveAd = adService.save(ad);
//        String uploadDir = "./ad-images/" + saveAd.getId();
//        FileUploadUtil.saveFile(uploadDir, mainMultipartFile, mainImageName);

        for (MultipartFile extraMultipart : extraMultipartFiles) {
            String fileName = StringUtils.cleanPath(extraMultipart.getOriginalFilename());

//            FileUploadUtil.saveFile(uploadDir, extraMultipart, fileName);
            ra.addFlashAttribute("message", "The product has been saved successfully");
        }
        return ResponseEntity.ok(ad);
    }

    @GetMapping("/ads/{adId}")
    public ResponseEntity<Ad> getOneAd(@PathVariable Long adId) {
        return ResponseEntity.ok(adService.getOne(adId));
    }

    @GetMapping("/ads")
    public ResponseEntity<List<Ad>> getAllAd() {
        return ResponseEntity.ok(adService.getAll());
    }

    @PutMapping("/ads")
    public ResponseEntity<Ad> updateAd(@RequestBody Ad ad) {
        if (ad.getId() == null) {
            throw new IllegalStateException("Id this ad does not exists");
        }
        return ResponseEntity.ok(adService.update(ad));
    }

    @DeleteMapping("/ads/{adId}")
    public ResponseEntity<String> deleteAd(@PathVariable Long adId) {
        return ResponseEntity.ok(adId + "- ad deleted");
    }

}


