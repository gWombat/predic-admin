package fr.gwombat.predicadmin.web.controller;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.web.form.PublisherForm;
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

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/publishers")
public class PublisherController {

    private static final Logger  logger                = LoggerFactory.getLogger(PublisherController.class);

    private static final String  PUBLISHERS_PAGE       = "publishers";
    private static final String  PUBLISHER_EDIT_PAGE   = "publisher-edit";
    private static final String  PUBLISHER_DETAIL_PAGE = "publisher-detail";

    private PublisherService     publisherService;
    private PublisherTransformer publisherTransformer;
    private CongregationService  congregationService;

    @Autowired
    public PublisherController(final PublisherService publisherService, final PublisherTransformer publisherTransformer, final CongregationService congregationService) {
        this.publisherService = publisherService;
        this.publisherTransformer = publisherTransformer;
        this.congregationService = congregationService;
    }

    @GetMapping
    public String allCongregationPublishers(Model model) {

        final List<Congregation> congregations = congregationService.getAllCongregations();
        final List<Publisher> publishers = publisherService.getByCongregation(congregations.get(0));

        if (publishers != null) {
            final List<PublisherVO> publisherVOS = new ArrayList<>(publishers.size());
            for (Publisher publisher : publishers) {
                PublisherVO publisherVo = publisherTransformer.toViewObject(publisher);
                publisherVOS.add(publisherVo);
            }

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
    public String detailPublisherPage(@PathVariable("id") final String identifier, Model model) {
        final Publisher publisher = publisherService.getByIdentifier(identifier);
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
        if (result.hasErrors()) {
            errors.reject("validation.error.global");
            return PUBLISHER_EDIT_PAGE;
        }

        Publisher publisher = publisherService.getByIdentifier(publisherForm.getIdentifier());
        publisher = publisherTransformer.toEntity(publisherForm, publisher);

        try {
            publisherService.save(publisher);
            redirectAttributes.addFlashAttribute("success", "page.profile.detail.update.success");

            return "redirect:/publishers/" + publisher.getIdentifier();
        } catch (Exception e) {
            model.addAttribute("error");
            logger.error(String.format("Error saving publisher [%s]: ", publisher), e);
            return PUBLISHER_EDIT_PAGE;
        }
    }

    @ModelAttribute("genders")
    public Gender[] getGenders() {
        return Gender.values();
    }

}
