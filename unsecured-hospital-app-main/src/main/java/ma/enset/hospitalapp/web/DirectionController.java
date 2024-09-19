package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Direction;
import ma.enset.hospitalapp.services.DirectionService;

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
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Direction> pageDirections = directionService.getDirectionsByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listDirections", pageDirections.getContent());
        model.addAttribute("pages", new int[pageDirections.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "directions";
    }

    @GetMapping("/deleteDirection")
    public String deleteDirection(@RequestParam(name = "id") Long id, 
                                  @RequestParam(name = "keyword", defaultValue = "") String keyword, 
                                  @RequestParam(name = "page", defaultValue = "0") int page, RedirectAttributes redirectAttributes) {
        directionService.deleteDirection(id);
        redirectAttributes.addFlashAttribute("message", "Direction supprimée avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formDirection")
    public String formDirection(Model model) {
        model.addAttribute("direction", new Direction());
        return "formDirection";
    }
    @PostMapping("/saveDirection")
    public String saveDirection(@Valid Direction direction,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        boolean isUpdate = direction.getId() != null;
    
        if (bindingResult.hasErrors()) {
            return "formDirection";
        }
    
        try {
            directionService.saveDirection(direction);
            String message = isUpdate ? "Direction modifiée avec succès." : "Direction ajoutée avec succès.";
            redirectAttributes.addFlashAttribute("message", message);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("code", "error.direction", e.getMessage());
            return "formDirection";
        }
    
        return "redirect:/index?keyword=" + keyword; // Inclure le keyword
    }
    

    @GetMapping("/editDirection")
public String editDirection(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "keyword", defaultValue = "") String keyword,
                             Model model) {
    Direction direction = directionService.getDirectionById(id).orElse(null);
    if (direction == null) {
        model.addAttribute("message", "Direction non trouvée.");
        model.addAttribute("alertClass", "alert-danger");
        return "redirect:/index?keyword=" + keyword;
    }
    model.addAttribute("direction", direction);
    model.addAttribute("keyword", keyword); // Ajout du keyword
    return "editDirection";
}

}
