package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Gestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface GestionService {
    Page<Gestion> getGestionsByKeyword(String keyword, PageRequest pageRequest);
    void deleteGestion(Long id);
    Gestion saveGestion(Gestion gestion);
    Optional<Gestion> getGestionById(Long id);
    Optional<Gestion> getGestionByLibelle(String libelle);
    Optional<Gestion> getGestionByCode(String code);
}
