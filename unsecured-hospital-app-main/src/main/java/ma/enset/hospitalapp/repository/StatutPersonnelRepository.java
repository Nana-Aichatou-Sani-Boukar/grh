package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.StatutPersonnel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutPersonnelRepository extends JpaRepository<StatutPersonnel, Long> {
    Page<StatutPersonnel> findByLibelleContains(String kw, Pageable pageable);
    Optional<StatutPersonnel> findByLibelle(String libelle);
}
