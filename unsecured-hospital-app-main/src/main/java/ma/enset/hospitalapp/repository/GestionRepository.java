package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Gestion;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestionRepository extends JpaRepository<Gestion, Long> {
    Page<Gestion> findByLibelleContains(String kw, Pageable pageable);
    Optional<Gestion> findByCode(String code);
    Optional<Gestion> findByLibelle(String libelle);
}
