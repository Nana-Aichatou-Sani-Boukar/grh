package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.StatutPersonnel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface StatutPersonnelService {
    Page<StatutPersonnel> getStatutPersonnelsByKeyword(String keyword, PageRequest pageRequest);
    void deleteStatutPersonnel(Long id);
    StatutPersonnel saveStatutPersonnel(StatutPersonnel statutPersonnel);
    Optional<StatutPersonnel> getStatutPersonnelById(Long id);
    Optional<StatutPersonnel> getStatutPersonnelByLibelle(String libelle);
}
