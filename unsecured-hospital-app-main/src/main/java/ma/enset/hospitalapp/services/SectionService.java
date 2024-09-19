package ma.enset.hospitalapp.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ma.enset.hospitalapp.entities.Section;

public interface SectionService {
    Page<Section> getSectionsByKeyword(String keyword, PageRequest pageRequest);
    Section saveSection(Section section);
    void deleteSection(Long id);
    Optional<Section> getSectionById(Long id);
}    