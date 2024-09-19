package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Profession;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    Page<Profession> findByLibelleContains(String kw, Pageable pageable);
    Optional<Profession> findByLibelle(String libelle);
}
