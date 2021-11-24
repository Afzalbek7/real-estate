package maroqand.uz.real_estate.repository;

import maroqand.uz.real_estate.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
