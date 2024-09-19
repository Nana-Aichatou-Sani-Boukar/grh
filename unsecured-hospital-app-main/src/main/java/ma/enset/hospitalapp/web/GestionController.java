package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Gestion;
import ma.enset.hospitalapp.services.GestionService;

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
public class GestionController {

    @Autowired
    private GestionService gestionService;

    @GetMapping("/gestions")
    public String gestions(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Gestion> pageGestions = gestionService.getGestionsByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listGestions", pageGestions.getContent());
        model.addAttribute("pages", new int[pageGestions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "gestions";
    }
    @GetMapping("/deleteGestion")
    public String deleteGestion(@RequestParam(name = "id") Long id, 
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword, 
                                  @RequestParam(name = "page", defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        gestionService.deleteGestion(id);
        redirectAttributes.addFlashAttribute("message", "Gestion supprimée avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/gestions?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formGestion")
    public String formGestion(Model model) {
        model.addAttribute("gestion", new Gestion());
        return "formGestion";
    }
    @PostMapping("/saveGestion")
    public String saveGestion(@Valid Gestion gestion,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        boolean isUpdate = gestion.getId() != null;
    
        if (bindingResult.hasErrors()) {
            return "formGestion";
        }
        try {
            gestionService.saveGestion(gestion);
            String message = isUpdate ? "Gestion modifiée avec succès." : "Gestion ajoutée avec succès.";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("code", "error.gestion", e.getMessage());
            return "formGestion";
        }
    
        return "redirect:/gestions?keyword=" + keyword; // Inclure le keyword
    }
    

    @GetMapping("/editGestion")
public String editGestion(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             Model model) {
    Gestion gestion = gestionService.getGestionById(id).orElse(null);
    if (gestion == null) {
        model.addAttribute("message", "Gestion non trouvée.");
        model.addAttribute("alertClass", "alert-danger");
        return "redirect:/gestions?keyword=" + keyword;
    }
    model.addAttribute("gestion", gestion);
    model.addAttribute("keyword", keyword); // Ajout du keyword
    return "editGestion";
}

}
