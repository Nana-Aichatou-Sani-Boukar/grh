package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Ville;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VilleRepository extends JpaRepository<Ville, Long> {
    Page<Ville> findByLibelleContains(String kw, Pageable pageable);
    Optional<Ville> findByLibelle(String libelle);
}
