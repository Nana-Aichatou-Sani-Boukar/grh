package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.StatutPersonnel;
import ma.enset.hospitalapp.services.StatutPersonnelService;

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
public class StatutPersonnelController {

    @Autowired
    private StatutPersonnelService statutPersonnelService;

    @GetMapping("/statutPersonnels")
    public String statutPersonnels(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<StatutPersonnel> pagestatutPersonnels = statutPersonnelService.getStatutPersonnelsByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listStatutPersonnels", pagestatutPersonnels.getContent());
        model.addAttribute("pages", new int[pagestatutPersonnels.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "statutPersonnels";
    }
    @GetMapping("/deleteStatutPersonnel")
    public String deletestatutPersonnel(@RequestParam(name = "id") Long id, 
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword, 
                                  @RequestParam(name = "page", defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        statutPersonnelService.deleteStatutPersonnel(id);
        redirectAttributes.addFlashAttribute("message", "Statut supprimée avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/statutPersonnels?page=" + page + "&keyword=" + keyword;
    }

     @GetMapping("/formStatutPersonnel")
    public String formStatutPersonnel(Model model) {
        model.addAttribute("statutPersonnel", new StatutPersonnel());
        return "formStatutPersonnel";
    }
    @PostMapping("/saveStatutPersonnel")
    public String saveStatutPersonnel(@Valid @ModelAttribute StatutPersonnel statutPersonnel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        boolean isUpdate = statutPersonnel.getId() != null;
    
        if (bindingResult.hasErrors()) {
            return "editStatutPersonnel";
        }
        try {
            statutPersonnelService.saveStatutPersonnel(statutPersonnel);
            String message = isUpdate ? "Statut modifiée avec succès." : "Statut ajoutée avec succès.";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelle", "error.statutPersonnel", e.getMessage());
            return "editStatutPersonnel";
        }
    
        return "redirect:/statutPersonnels?keyword=" + keyword;
    }    

    @GetMapping("/editStatutPersonnel")
    public String editStatutPersonnel(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             Model model) {
    StatutPersonnel statutPersonnel = statutPersonnelService.getStatutPersonnelById(id).orElse(null);
    if (statutPersonnel == null) {
        model.addAttribute("message", "statutPersonnel non trouvée.");
        model.addAttribute("alertClass", "alert-danger");
        return "redirect:/statutPersonnels?keyword=" + keyword;
    }
    model.addAttribute("statutPersonnel", statutPersonnel);
    model.addAttribute("keyword", keyword); // Ajout du keyword
    return "editStatutPersonnel";
}

}
