package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Profession;
import ma.enset.hospitalapp.services.ProfessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    @GetMapping("/professions")
    public String professions(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Profession> pageProfessions = professionService.getProfessionsByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listProfessions", pageProfessions.getContent());
        model.addAttribute("pages", new int[pageProfessions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "professions";
    }
    @GetMapping("/deleteProfession")
    public String deleteProfession(@RequestParam(name = "id") Long id, 
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword, 
                                  @RequestParam(name = "page", defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        professionService.deleteProfession(id);
        redirectAttributes.addFlashAttribute("message", "Profession supprimée avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/professions?page=" + page + "&keyword=" + keyword;
    }
    @GetMapping("/formProfession")
    public String formProfession(Model model) {
        model.addAttribute("profession", new Profession());
        return "formProfession";
    }
    @PostMapping("/saveProfession")
    public String saveProfession(@Valid Profession profession,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        boolean isUpdate = profession.getId() != null;
    
        if (bindingResult.hasErrors()) {
            return "formProfession";
        }
        try {
            professionService.saveProfession(profession);
            String message = isUpdate ? "Profession modifiée avec succès." : "Profession ajoutée avec succès.";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelle", "error.Profession", e.getMessage());
            return "formProfession";
        }
    
        return "redirect:/professions?keyword=" + keyword; // Inclure le keyword
    }
    

    @GetMapping("/editProfession")
    public String editProfession(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword,
                         Model model) {
    Profession profession = professionService.getProfessionById(id).orElse(null);
    if (profession == null) {
        model.addAttribute("message", "Profession non trouvée.");
        model.addAttribute("alertClass", "alert-danger");
        return "redirect:/professions?keyword=" + keyword;
    }
    model.addAttribute("Profession", profession); // Nom correct pour le modèle
    model.addAttribute("keyword", keyword); // Ajout du keyword
    return "editProfession";
    }
}
