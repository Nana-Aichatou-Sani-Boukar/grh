package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Profession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ProfessionService {
    Page<Profession> getProfessionsByKeyword(String keyword, PageRequest pageRequest);
    void deleteProfession(Long id);
    Profession saveProfession(Profession Profession);
    Optional<Profession> getProfessionById(Long id);
    Optional<Profession> getProfessionByLibelle(String libelle);
}
