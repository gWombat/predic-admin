package fr.gwombat.predicadmin.web.controller;

import javax.validation.Valid;

import fr.gwombat.predicadmin.web.vo.PublisherVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.web.form.PublisherForm;

@Controller
@RequestMapping("/publishers")
public class PublisherController {
    
    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private PublisherService publisherService;

    @Autowired
    public PublisherController(final PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/{id}")
    public String detailPublisherPage(@PathVariable("id") final String identifier, Model model) {
        final Publisher publisher = publisherService.getByIdentifier(identifier);
        final PublisherVO publisherVo = new PublisherVO(publisher);
        model.addAttribute("publisher", publisherVo);

        return "publisher-detail";
    }

    @GetMapping("/{id}/edit")
    public String editPublisherPage(@PathVariable("id") final String identifier, Model model) {
        final Publisher publisher = publisherService.getByIdentifier(identifier);
        final PublisherForm publisherForm = new PublisherForm(publisher);
        model.addAttribute("publisher", publisherForm);

        return "publisher-edit";
    }

    @PostMapping
    public String saveOrUpdatePublisher(Model model, @ModelAttribute("publisher") @Valid PublisherForm publisherForm, BindingResult result, Errors errors, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            errors.reject("validation.error.global");
            return "publisher-edit";
        }

        Publisher publisher = publisherService.getByIdentifier(publisherForm.getIdentifier());
        publisher = publisherForm.toEntity(publisher);

        try {
            publisherService.save(publisher);
            redirectAttributes.addFlashAttribute("success", "page.profile.detail.update.success");
            
            return "redirect:/publishers/" + publisher.getIdentifier();
        } catch (Exception e) {
            model.addAttribute("error");
            logger.error(String.format("Error saving publisher [%s]: ", publisher), e);
            return "publisher-edit";
        }
    }

    @ModelAttribute("genders")
    public Gender[] getGenders() {
        return Gender.values();
    }

}
