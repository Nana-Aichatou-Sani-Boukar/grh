package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Section;
import ma.enset.hospitalapp.repository.ServiceRepository;
import ma.enset.hospitalapp.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/sections")
    public String sections(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Section> pageSections = sectionService.getSectionsByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listsections", pageSections.getContent());
        model.addAttribute("pages", new int[pageSections.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "sections";
    }

    @GetMapping("/deleteSection")
    public String deleteSection(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                RedirectAttributes redirectAttributes) {
        sectionService.deleteSection(id);
        redirectAttributes.addFlashAttribute("message", "Section supprimé avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/sections?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formSection")
    public String formSection(Model model) {
        model.addAttribute("section", new Section());
        model.addAttribute("services", serviceRepository.findAll());  // Charger tous les services pour le dropdown
        return "formSection";
    }

    @PostMapping("/saveSection")
    public String saveSection(@Valid @ModelAttribute("section") Section section,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("services", serviceRepository.findAll());  // Recharger les services en cas d'erreur
            return "formSection";
        }
        
        // Sauvegarde du section
        sectionService.saveSection(section);
        redirectAttributes.addFlashAttribute("message", "Section sauvegardé avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/sections";
    }

    @GetMapping("/editSection")
    public String editSection(@RequestParam(name = "id") Long id, Model model) {
        Section section = sectionService.getSectionById(id).orElse(null);
        if (section == null) {
            return "redirect:/sections";  // Rediriger si la section n'existe pas
        }
        model.addAttribute("section", section);
        model.addAttribute("services", serviceRepository.findAll());  // Recharger les directions pour le formulaire d'édition
        return "editSection";
    }
}
