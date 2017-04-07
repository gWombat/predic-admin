package fr.gwombat.predicadmin.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.alert.AlertMessage;
import fr.gwombat.predicadmin.web.alert.DangerAlertMessage;
import fr.gwombat.predicadmin.web.alert.SuccessAlertMessage;
import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;
import fr.gwombat.predicadmin.web.transformer.MeetingAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.validator.MeetingAttendanceValidator;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;

@Controller
@RequestMapping("/attendance")
public class MeetingAttendanceController {

    private static final Logger                logger = LoggerFactory.getLogger(MeetingAttendanceController.class);

    private final MeetingAttendanceService     meetingAttendanceService;
    private final MonthAttendanceService       monthAttendanceService;
    private final CongregationService          congregationService;

    private final MeetingAttendanceTransformer meetingAttendanceTransformer;
    private final MonthAttendanceTransformer   monthAttendanceTransformer;

    private final MessageSource                messageSource;

    @Autowired
    public MeetingAttendanceController(final MeetingAttendanceService meetingAttendanceService, final MeetingAttendanceTransformer meetingAttendancetransformer, final CongregationService congregationService, final MonthAttendanceService monthAttendanceService, final MonthAttendanceTransformer monthAttendanceTransformer, final MessageSource messageSource) {

        this.meetingAttendanceService = meetingAttendanceService;
        this.meetingAttendanceTransformer = meetingAttendancetransformer;
        this.congregationService = congregationService;
        this.monthAttendanceService = monthAttendanceService;
        this.monthAttendanceTransformer = monthAttendanceTransformer;
        this.messageSource = messageSource;
    }
    
    @ModelAttribute("monthAttendance")
    public MonthAttendanceVO getMonthAttendance(){
        final Period period = PeriodBuilder.init().month(4).year(2017).build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(congregationService.getCurrentCongregation(), period);
        final MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
        return monthAttendanceVo;
    }

    @GetMapping
    public String attendancePage(Model model) {
        final MeetingAttendanceForm meetingAttendance = new MeetingAttendanceForm();
        model.addAttribute("attendance", meetingAttendance);
        
        return "attendances";
    }

    @PostMapping
    public String saveOrUpdateMeetingAttendance(@ModelAttribute("attendance") @Valid MeetingAttendanceForm attendanceForm, 
            BindingResult result, Errors errors, RedirectAttributes redirectAttributes) {

        Validator validator = new MeetingAttendanceValidator(messageSource);
        validator.validate(attendanceForm, errors);

        if (result.hasErrors())
            return "attendances";

        AlertMessage message = null;
        MeetingAttendance meetingAttendance = null;
        try {
            meetingAttendance = meetingAttendanceService.getByIdentifier(attendanceForm.getIdentifier());
            meetingAttendance = meetingAttendanceTransformer.toEntity(attendanceForm, meetingAttendance);
            meetingAttendance.setCongregation(congregationService.getCurrentCongregation());
            meetingAttendanceService.save(meetingAttendance);

            message = new SuccessAlertMessage();
            message.setLabelCode("page.meeting.attendance.creation.success");
        } catch (DataIntegrityViolationException e) {
            message = new DangerAlertMessage();
            message.setLabelCode("page.meeting.attendance.already.exists");
            logger.info(meetingAttendance + " " + e.getMostSpecificCause().getMessage());
        } catch (Exception e) {
            message = new DangerAlertMessage();
            message.setLabelCode("validation.error.internal");
            logger.error(String.format("Error saving attendance [%s]: %s", meetingAttendance, e.getMessage()));
        }

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/attendance";
    }

    @PostMapping("/{id}/delete")
    public String deleteAttendance(@PathVariable("id") final String identifier, Model model, RedirectAttributes redirectAttributes) {
        AlertMessage message = null;

        try {
            meetingAttendanceService.deleteByIdentifier(identifier);

            message = new SuccessAlertMessage();
            message.setLabelCode("page.meeting.attendance.delete.success");
        } catch (Exception e) {
            message = new DangerAlertMessage();
            message.setLabelCode("validation.error.internal");
            model.addAttribute("message", message);
            logger.error(String.format("Error deleting attendance [%s]: ", identifier), e);
        }

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/attendance";
    }

}
