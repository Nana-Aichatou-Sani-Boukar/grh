package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Profession;
import ma.enset.hospitalapp.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionRepository professionRepository;

    @Override
    public Page<Profession> getProfessionsByKeyword(String keyword, PageRequest pageRequest) {
        return professionRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public void deleteProfession(Long id) {
        professionRepository.deleteById(id);
    }

    @Override
    public Profession saveProfession(Profession profession) {

        // Vérifiez si une Profession avec le même libellé existe déjà, et si ce n'est pas la même Profession
        Optional<Profession> existingProfessionByLibelle = professionRepository.findByLibelle(profession.getLibelle());
        if (existingProfessionByLibelle.isPresent() && 
            !existingProfessionByLibelle.get().getId().equals(profession.getId())) {
            throw new IllegalArgumentException("Une Profession avec ce libellé existe déjà.");
        }

        // Sauvegardez la Profession
        return professionRepository.save(profession);
    }

    @Override
    public Optional<Profession> getProfessionById(Long id) {
        return professionRepository.findById(id);
    }

    @Override
    public Optional<Profession> getProfessionByLibelle(String libelle) {
        return professionRepository.findByLibelle(libelle);
    }
}