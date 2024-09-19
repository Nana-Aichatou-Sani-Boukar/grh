package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Ville;
import ma.enset.hospitalapp.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VilleServiceImpl implements VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Override
    public Page<Ville> getVillesByKeyword(String keyword, PageRequest pageRequest) {
        return villeRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public void deleteVille(Long id) {
        villeRepository.deleteById(id);
    }

    @Override
    public Ville saveVille(Ville ville) {

        // Vérifiez si une Ville avec le même libellé existe déjà, et si ce n'est pas la même Ville
        Optional<Ville> existingVilleByLibelle = villeRepository.findByLibelle(ville.getLibelle());
        if (existingVilleByLibelle.isPresent() && 
            !existingVilleByLibelle.get().getId().equals(ville.getId())) {
            throw new IllegalArgumentException("Une ville avec ce libellé existe déjà.");
        }

        // Sauvegardez la Ville
        return villeRepository.save(ville);
    }

    @Override
    public Optional<Ville> getVilleById(Long id) {
        return villeRepository.findById(id);
    }

    @Override
    public Optional<Ville> getVilleByLibelle(String libelle) {
        return villeRepository.findByLibelle(libelle);
    }
}