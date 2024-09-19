package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Direction;
import ma.enset.hospitalapp.repository.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private DirectionRepository directionRepository;

    @Override
    public Page<Direction> getDirectionsByKeyword(String keyword, PageRequest pageRequest) {
        return directionRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public void deleteDirection(Long id) {
        directionRepository.deleteById(id);
    }

    @Override
    public Direction saveDirection(Direction direction) {
        // Vérifiez si une direction avec le même code existe déjà, et si ce n'est pas la même direction
        Optional<Direction> existingDirectionByCode = directionRepository.findByCode(direction.getCode());
        if (existingDirectionByCode.isPresent() && 
            !existingDirectionByCode.get().getId().equals(direction.getId())) {
            throw new IllegalArgumentException("Une direction avec ce code existe déjà.");
        }

        // Vérifiez si une direction avec le même libellé existe déjà, et si ce n'est pas la même direction
        Optional<Direction> existingDirectionByLibelle = directionRepository.findByLibelle(direction.getLibelle());
        if (existingDirectionByLibelle.isPresent() && 
            !existingDirectionByLibelle.get().getId().equals(direction.getId())) {
            throw new IllegalArgumentException("Une direction avec ce libellé existe déjà.");
        }

        // Sauvegardez la direction
        return directionRepository.save(direction);
    }

    @Override
    public Optional<Direction> getDirectionById(Long id) {
        return directionRepository.findById(id);
    }

    @Override
    public Optional<Direction> getDirectionByLibelle(String libelle) {
        return directionRepository.findByLibelle(libelle);
    }

    @Override
    public Optional<Direction> getDirectionByCode(String code) {
        return directionRepository.findByCode(code);
    }
}
