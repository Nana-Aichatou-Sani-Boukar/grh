package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.StatutPersonnel;
import ma.enset.hospitalapp.repository.StatutPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatutPersonnelServiceImpl implements StatutPersonnelService {

    @Autowired
    private StatutPersonnelRepository statutPersonnelRepository;

    @Override
    public Page<StatutPersonnel> getStatutPersonnelsByKeyword(String keyword, PageRequest pageRequest) {
        return statutPersonnelRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public void deleteStatutPersonnel(Long id) {
        statutPersonnelRepository.deleteById(id);
    }

    @Override
    public StatutPersonnel saveStatutPersonnel(StatutPersonnel statutPersonnel) {
        // Vérifiez si une StatutPersonnel avec le même libellé existe déjà, et si ce n'est pas la même StatutPersonnel
        Optional<StatutPersonnel> existingStatutPersonnelByLibelle = statutPersonnelRepository.findByLibelle(statutPersonnel.getLibelle());
        if (existingStatutPersonnelByLibelle.isPresent() && 
            !existingStatutPersonnelByLibelle.get().getId().equals(statutPersonnel.getId())) {
            throw new IllegalArgumentException("Une StatutPersonnel avec ce libellé existe déjà.");
        }

        // Sauvegardez la StatutPersonnel
        return statutPersonnelRepository.save(statutPersonnel);
    }

    @Override
    public Optional<StatutPersonnel> getStatutPersonnelById(Long id) {
        return statutPersonnelRepository.findById(id);
    }

    @Override
    public Optional<StatutPersonnel> getStatutPersonnelByLibelle(String libelle) {
        return statutPersonnelRepository.findByLibelle(libelle);
    }
}