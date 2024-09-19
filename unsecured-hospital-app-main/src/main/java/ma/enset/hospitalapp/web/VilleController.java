package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Ville;
import ma.enset.hospitalapp.services.VilleService;

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
public class VilleController {

    @Autowired
    private VilleService villeService;

    @GetMapping("/villes")
    public String villes(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Ville> pageVilles = villeService.getVillesByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listVilles", pageVilles.getContent());
        model.addAttribute("pages", new int[pageVilles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "villes";
    }
    @GetMapping("/deleteVille")
    public String deleteVille(@RequestParam(name = "id") Long id, 
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword, 
                                  @RequestParam(name = "page", defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        villeService.deleteVille(id);
        redirectAttributes.addFlashAttribute("message", "Ville supprimée avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/villes?page=" + page + "&keyword=" + keyword;
    }
    @GetMapping("/formVille")
    public String formVille(Model model) {
        model.addAttribute("ville", new Ville());
        return "formVille";
    }
    @PostMapping("/saveVille")
    public String saveVille(@Valid Ville ville,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        boolean isUpdate = ville.getId() != null;
    
        if (bindingResult.hasErrors()) {
            return "formVille";
        }
        try {
            villeService.saveVille(ville);
            String message = isUpdate ? "Ville modifiée avec succès." : "Ville ajoutée avec succès.";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("libelle", "error.ville", e.getMessage());
            return "formVille";
        }
    
        return "redirect:/villes?keyword=" + keyword; // Inclure le keyword
    }
    

    @GetMapping("/editVille")
    public String editVille(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword,
                         Model model) {
    Ville ville = villeService.getVilleById(id).orElse(null);
    if (ville == null) {
        model.addAttribute("message", "Ville non trouvée.");
        model.addAttribute("alertClass", "alert-danger");
        return "redirect:/villes?keyword=" + keyword;
    }
    model.addAttribute("ville", ville); // Nom correct pour le modèle
    model.addAttribute("keyword", keyword); // Ajout du keyword
    return "editVille";
}


}
