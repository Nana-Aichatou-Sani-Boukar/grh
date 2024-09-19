package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.ServiceEntity;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    Page<ServiceEntity> findByLibelleContains(String kw, Pageable pageable);
    Optional<ServiceEntity> findByLibelle(String libelle);
}
