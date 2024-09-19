package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface VilleService {
    Page<Ville> getVillesByKeyword(String keyword, PageRequest pageRequest);
    void deleteVille(Long id);
    Ville saveVille(Ville ville);
    Optional<Ville> getVilleById(Long id);
    Optional<Ville> getVilleByLibelle(String libelle);
}
