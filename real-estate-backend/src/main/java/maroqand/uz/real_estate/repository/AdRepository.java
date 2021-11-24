package maroqand.uz.real_estate.repository;

import maroqand.uz.real_estate.domain.Ad;
import maroqand.uz.real_estate.domain.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
