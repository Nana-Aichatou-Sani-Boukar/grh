package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.Section;
import ma.enset.hospitalapp.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public Page<Section> getSectionsByKeyword(String keyword, PageRequest pageRequest) {
        return sectionRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public Section saveSection(Section service) {
        return sectionRepository.save(service);
    }

    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    @Override
    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }
}
