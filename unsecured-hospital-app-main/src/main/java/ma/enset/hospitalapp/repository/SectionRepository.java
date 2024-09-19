package ma.enset.hospitalapp.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.enset.hospitalapp.entities.Section;

public interface SectionRepository extends JpaRepository <Section, Long>{
    Page<Section> findByLibelleContains(String kw, Pageable pageable);
    Optional<Section> findByLibelle(String libelle);
}
