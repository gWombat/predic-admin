package fr.gwombat.predicadmin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.PublisherService;

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
        model.addAttribute("publisher", publisher);
        
        return "publisher-edit";
    }

}
