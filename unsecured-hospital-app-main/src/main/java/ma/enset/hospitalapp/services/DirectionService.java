package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Direction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface DirectionService {
    Page<Direction> getDirectionsByKeyword(String keyword, PageRequest pageRequest);
    void deleteDirection(Long id);
    Direction saveDirection(Direction direction);
    Optional<Direction> getDirectionById(Long id);
    Optional<Direction> getDirectionByLibelle(String libelle);
    Optional<Direction> getDirectionByCode(String code);
}
