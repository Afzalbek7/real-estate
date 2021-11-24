package maroqand.uz.real_estate.web.rest;

import maroqand.uz.real_estate.domain.Announcement;
import maroqand.uz.real_estate.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService adService) {
        this.announcementService = adService;
    }

    @PostMapping(value = "/announcements")
    public ResponseEntity<Announcement> add(@RequestBody Announcement announcement, @RequestParam(value = "imageId") Long imageId) {
        return ResponseEntity.ok(announcementService.create(announcement, imageId));
    }

    @GetMapping("/announcements/{announcementId}")
    public ResponseEntity<Announcement> getAd(@PathVariable Long announcementId) {
        return ResponseEntity.ok(announcementService.getOne(announcementId));
    }

    @GetMapping("/announcements")
    public ResponseEntity<List<Announcement>> getAllAnnouncement(){
        return ResponseEntity.ok(announcementService.getAll());
    }

    @PutMapping("/announcements")
    public ResponseEntity<Announcement> updateAd(@RequestBody Announcement announcement) {
        if (announcement.getId() == null) {
            throw new IllegalStateException("Id is null");
        }
        return ResponseEntity.ok(announcementService.update(announcement));
    }
}
