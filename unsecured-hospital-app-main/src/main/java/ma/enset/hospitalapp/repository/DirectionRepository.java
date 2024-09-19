package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Direction;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
    Page<Direction> findByLibelleContains(String kw, Pageable pageable);
    Optional<Direction> findByCode(String code);
    Optional<Direction> findByLibelle(String libelle);
}
