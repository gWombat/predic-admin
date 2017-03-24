package fr.gwombat.predicadmin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.web.vo.PublisherForm;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    
    private PublisherService publisherService;
    
    @Autowired
    public PublisherController(final PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public String editPublisherPage(@PathVariable("id") final String identifier, Model model) {
        
        final Publisher publisher = publisherService.getByIdentifier(identifier);
        final PublisherForm publisherForm = new PublisherForm(publisher);
        model.addAttribute("publisher", publisherForm);
        
        return "publisher-edit";
    }
    
    @ModelAttribute("genders")
    public Gender[] getGenders(){
        return Gender.values();
    }

}
