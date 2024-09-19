package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Gestion;
import ma.enset.hospitalapp.repository.GestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GestionServiceImpl implements GestionService {

    @Autowired
    private GestionRepository gestionRepository;

    @Override
    public Page<Gestion> getGestionsByKeyword(String keyword, PageRequest pageRequest) {
        return gestionRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public void deleteGestion(Long id) {
        gestionRepository.deleteById(id);
    }

    @Override
    public Gestion saveGestion(Gestion gestion) {
        // Vérifiez si une Gestion avec le même code existe déjà, et si ce n'est pas la même Gestion
        Optional<Gestion> existingGestionByCode = gestionRepository.findByCode(gestion.getCode());
        if (existingGestionByCode.isPresent() && 
            !existingGestionByCode.get().getId().equals(gestion.getId())) {
            throw new IllegalArgumentException("Une gestion avec ce code existe déjà.");
        }

        // Vérifiez si une Gestion avec le même libellé existe déjà, et si ce n'est pas la même Gestion
        Optional<Gestion> existingGestionByLibelle = gestionRepository.findByLibelle(gestion.getLibelle());
        if (existingGestionByLibelle.isPresent() && 
            !existingGestionByLibelle.get().getId().equals(gestion.getId())) {
            throw new IllegalArgumentException("Une Gestion avec ce libellé existe déjà.");
        }

        // Sauvegardez la Gestion
        return gestionRepository.save(gestion);
    }

    @Override
    public Optional<Gestion> getGestionById(Long id) {
        return gestionRepository.findById(id);
    }

    @Override
    public Optional<Gestion> getGestionByLibelle(String libelle) {
        return gestionRepository.findByLibelle(libelle);
    }

    @Override
    public Optional<Gestion> getGestionByCode(String code) {
        return gestionRepository.findByCode(code);
    }
}