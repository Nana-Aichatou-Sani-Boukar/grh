package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.ServiceEntity;
import ma.enset.hospitalapp.repository.DirectionRepository;
import ma.enset.hospitalapp.services.ServiceService;
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
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private DirectionRepository directionRepository;

    @GetMapping("/services")
    public String services(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<ServiceEntity> pageServices = serviceService.getServicesByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("listServices", pageServices.getContent());
        model.addAttribute("pages", new int[pageServices.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "services";
    }

    @GetMapping("/deleteService")
    public String deleteService(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                RedirectAttributes redirectAttributes) {
        serviceService.deleteService(id);
        redirectAttributes.addFlashAttribute("message", "Service supprimé avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/services?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formService")
    public String formService(Model model) {
        model.addAttribute("service", new ServiceEntity());
        model.addAttribute("directions", directionRepository.findAll());  // Charger toutes les directions pour le dropdown
        return "formService";
    }

    @PostMapping("/saveService")
    public String saveService(@Valid @ModelAttribute("service") ServiceEntity service,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("directions", directionRepository.findAll());  // Recharger les directions en cas d'erreur
            return "formService";
        }
        
        // Sauvegarde du service
        serviceService.saveService(service);
        redirectAttributes.addFlashAttribute("message", "Service sauvegardé avec succès.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/services";
    }

    @GetMapping("/editService")
    public String editService(@RequestParam(name = "id") Long id, Model model) {
        ServiceEntity service = serviceService.getServiceById(id).orElse(null);
        if (service == null) {
            return "redirect:/services";  // Rediriger si le service n'existe pas
        }
        model.addAttribute("service", service);
        model.addAttribute("directions", directionRepository.findAll());  // Recharger les directions pour le formulaire d'édition
        return "editService";
    }
}
