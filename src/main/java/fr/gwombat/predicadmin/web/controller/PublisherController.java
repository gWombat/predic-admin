package fr.gwombat.predicadmin.web.controller;

import fr.gwombat.predicadmin.exception.ResourceNotFoundException;
import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.web.alert.AlertMessage;
import fr.gwombat.predicadmin.web.alert.SuccessAlertMessage;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.session.SessionBean;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

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
    private SessionBean          sessionBean;

    @Autowired
    public PublisherController(final PublisherService publisherService,
                               final PublisherTransformer publisherTransformer,
                               final CongregationService congregationService,
                               final SessionBean sessionBean) {
        this.publisherService = publisherService;
        this.publisherTransformer = publisherTransformer;
        this.congregationService = congregationService;
        this.sessionBean = sessionBean;
    }

    @GetMapping
    public String allCongregationPublishers(Model model) {

        final List<Congregation> congregations = congregationService.getAllCongregations();
        sessionBean.setCongregation(congregations.get(0));

        final List<Publisher> publishers = publisherService.getByCongregation(sessionBean.getCongregation());

        if(publishers != null) {
            final List<PublisherVO> publisherVOS = new ArrayList<>(publishers.size());
            for(Publisher publisher : publishers) {
                PublisherVO publisherVo = publisherTransformer.toViewObject(publisher);
                publisherVOS.add(publisherVo);
            }

            model.addAttribute("congregation", sessionBean.getCongregation());
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
            publisher.setCongregation(sessionBean.getCongregation());

        try {
            publisherService.save(publisher);
            
            final AlertMessage message = new SuccessAlertMessage();
            if(isNew)
                message.setLabelCode("page.profile.detail.creation.success");
            else
                message.setLabelCode("page.profile.detail.update.success");
            redirectAttributes.addFlashAttribute("message", message);

            return "redirect:/publishers/" + publisher.getIdentifier();
        } catch(Exception e) {
            model.addAttribute("error", "validation.error.internal");
            logger.error(String.format("Error saving publisher [%s]: ", publisher), e);
            return PUBLISHER_EDIT_PAGE;
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deletePublisher(@PathVariable("id") final String identifier, RedirectAttributes redirectAttributes){
        try{
            publisherService.deleteByIdentifier(identifier);
            redirectAttributes.addFlashAttribute("delete_success", "");
        }
        catch(Exception e){
            
        }
        
        return "redirect:/publishers";
    }

    @ModelAttribute("genders")
    public Gender[] getGenders() {
        return Gender.values();
    }

}
