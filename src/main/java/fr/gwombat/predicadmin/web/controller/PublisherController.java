package fr.gwombat.predicadmin.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import fr.gwombat.predicadmin.exception.ResourceNotFoundException;
import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.web.alert.AlertMessage;
import fr.gwombat.predicadmin.web.alert.DangerAlertMessage;
import fr.gwombat.predicadmin.web.alert.SuccessAlertMessage;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private static final String PUBLISHERS_PAGE       = "publishers";
    private static final String PUBLISHER_EDIT_PAGE   = "publisher-edit";
    private static final String PUBLISHER_DETAIL_PAGE = "publisher-detail";

    private PublisherService     publisherService;
    private PublisherTransformer publisherTransformer;
    private CongregationService  congregationService;

    @Autowired
    public PublisherController(final PublisherService publisherService,
                               final PublisherTransformer publisherTransformer,
                               final CongregationService congregationService) {
        this.publisherService = publisherService;
        this.publisherTransformer = publisherTransformer;
        this.congregationService = congregationService;
    }

    @GetMapping
    public String allCongregationPublishers(Model model) {

        final List<Publisher> publishers = publisherService.getByCongregation(congregationService.getCurrentCongregation());

        if(publishers != null) {
            final List<PublisherVO> publisherVOS = new ArrayList<>(publishers.size());
            for(Publisher publisher : publishers) {
                PublisherVO publisherVo = publisherTransformer.toViewObject(publisher);
                publisherVOS.add(publisherVo);
            }

            model.addAttribute("congregation", congregationService.getCurrentCongregation());
            model.addAttribute("publishers", publisherVOS);
            model.addAttribute("nbPublishers", publisherVOS.size());
        }

        return PUBLISHERS_PAGE;
    }

    @GetMapping("/add")
    public String showAddPublisherForm(Model model) {
        final PublisherForm publisherForm = new PublisherForm();

        model.addAttribute("publisher", publisherForm);
        model.addAttribute("newPublisher", true);

        return PUBLISHER_EDIT_PAGE;
    }

    @GetMapping("/{id}")
    public String detailPublisherPage(@PathVariable("id") final String identifier, Model model, HttpServletRequest request) {
        final Publisher publisher = publisherService.getByIdentifier(identifier);

        if(publisher == null)
            throw new ResourceNotFoundException(identifier, request.getRequestURI());

        final PublisherVO publisherVo = publisherTransformer.toViewObject(publisher);
        model.addAttribute("publisher", publisherVo);

        return PUBLISHER_DETAIL_PAGE;
    }

    @GetMapping("/{id}/edit")
    public String editPublisherPage(@PathVariable("id") final String identifier, Model model) {
        final Publisher publisher = publisherService.getByIdentifier(identifier);
        final PublisherForm publisherForm = publisherTransformer.toFormObject(publisher);
        model.addAttribute("publisher", publisherForm);

        return PUBLISHER_EDIT_PAGE;
    }

    @PostMapping
    public String saveOrUpdatePublisher(Model model, @ModelAttribute("publisher") @Valid PublisherForm publisherForm, BindingResult result, Errors errors, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            errors.reject("validation.error.global");
            return PUBLISHER_EDIT_PAGE;
        }

        boolean isNew = false;

        Publisher publisher = publisherService.getByIdentifier(publisherForm.getIdentifier());
        if(publisher == null)
            isNew = true;

        publisher = publisherTransformer.toEntity(publisherForm, publisher);
        if(isNew)
            publisher.setCongregation(congregationService.getCurrentCongregation());

        AlertMessage message = null;
        try {
            publisherService.save(publisher);
            
            message = new SuccessAlertMessage();
            if(isNew)
                message.setLabelCode("page.profile.detail.creation.success");
            else
                message.setLabelCode("page.profile.detail.update.success");
            redirectAttributes.addFlashAttribute("message", message);

            return "redirect:/publishers/" + publisher.getIdentifier();
        } catch(Exception e) {
            message = new DangerAlertMessage();
            message.setLabelCode("validation.error.internal");
            model.addAttribute("message", message);
            
            logger.error(String.format("Error saving publisher [%s]: ", publisher), e);
            return PUBLISHER_EDIT_PAGE;
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deletePublisher(@PathVariable("id") final String identifier, Model model, RedirectAttributes redirectAttributes){
        AlertMessage message = null;
        try{
            message = new SuccessAlertMessage();
            
            publisherService.deleteByIdentifier(identifier);
            message.setLabelCode("page.profile.detail.delete.success");
            redirectAttributes.addFlashAttribute("message", message);
        }
        catch(Exception e){
            message = new DangerAlertMessage();
            message.setLabelCode("validation.error.internal");
            model.addAttribute("message", message);
            
            logger.error(String.format("Error deleting publisher [%s]: ", identifier), e);
            return PUBLISHER_EDIT_PAGE;
        }
        
        return "redirect:/publishers";
    }

    @ModelAttribute("genders")
    public Gender[] getGenders() {
        return Gender.values();
    }

}
