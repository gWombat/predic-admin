package fr.gwombat.predicadmin.web.controller;

import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.alert.AlertMessage;
import fr.gwombat.predicadmin.web.alert.DangerAlertMessage;
import fr.gwombat.predicadmin.web.alert.SuccessAlertMessage;
import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;
import fr.gwombat.predicadmin.web.transformer.GlobalAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.MeetingAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.YearAttendanceTransformer;
import fr.gwombat.predicadmin.web.validator.MeetingAttendanceValidator;
import fr.gwombat.predicadmin.web.vo.GlobalAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

@Controller
@RequestMapping("/attendance")
public class MeetingAttendanceController {

    private static final Logger          logger = LoggerFactory.getLogger(MeetingAttendanceController.class);

    private MeetingAttendanceService     meetingAttendanceService;
    private YearAttendanceService        yearAttendanceService;
    private MonthAttendanceService       monthAttendanceService;
    private CongregationService          congregationService;

    private MeetingAttendanceTransformer meetingAttendanceTransformer;
    private YearAttendanceTransformer    yearAttendanceTransformer;
    private MonthAttendanceTransformer   monthAttendanceTransformer;
    private GlobalAttendanceTransformer  globalAttendanceTransformer;

    private MessageSource                messageSource;

    @ModelAttribute("yearAttendance")
    public YearAttendanceVO getYearAttendance() {
        final TheocraticYear year = new TheocraticYear();
        final YearAttendance yearAttendance = yearAttendanceService.getAttendanceForYear(congregationService.getCurrentCongregation(), year);
        final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(yearAttendance);
        return yearAttendanceVo;
    }

    @ModelAttribute("currentMonthAttendance")
    public MonthAttendanceVO getCurrentMonthAttendance() {
        final Period currentPeriod = PeriodBuilder.create().build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(congregationService.getCurrentCongregation(), currentPeriod);
        MonthAttendanceVO attendanceVO = monthAttendanceTransformer.toViewObject(monthAttendance);
        
        return attendanceVO;
    }

    @ModelAttribute("globalAttendance")
    public GlobalAttendanceVO getGlobalAttendance() {
        final List<YearAttendance> attendances = yearAttendanceService.getAttendancesForCongregation(congregationService.getCurrentCongregation());
        final GlobalAttendanceVO globalAttendanceVo = globalAttendanceTransformer.toViewObject(attendances);
        return globalAttendanceVo;
    }

    @ModelAttribute("monthNames")
    public List<String> getMonthsNames() {
        final Locale locale = LocaleContextHolder.getLocale();
        final String[] monthNames = DateFormatSymbols.getInstance(locale).getMonths();
        return convertArrayToList(monthNames);
    }

    @ModelAttribute("shortMonthNames")
    public List<String> getShortMonthsNames() {
        final Locale locale = LocaleContextHolder.getLocale();
        final String[] monthNames = DateFormatSymbols.getInstance(locale).getShortMonths();
        return convertArrayToList(monthNames);
    }

    @ModelAttribute("shortMonthNamesForTheocraticYear")
    public List<String> getShortMonthsNamesForTheocraticYear() {
        final List<String> shortMonthNames = getShortMonthsNames();

        final String[] orderedMonthNames = new String[12];
        for (int i = 0; i < shortMonthNames.size(); i++)
            orderedMonthNames[(i + 4) % 12] = shortMonthNames.get(i);

        return convertArrayToList(orderedMonthNames);
    }

    @ModelAttribute("weekdaysNames")
    public List<String> getWeekdaysNames() {
        final Locale locale = LocaleContextHolder.getLocale();
        final String[] weekdaysNames = DateFormatSymbols.getInstance(locale).getWeekdays();
        return convertArrayToList(weekdaysNames);
    }

    @GetMapping
    public String attendancePage(Model model) {
        final MeetingAttendanceForm meetingAttendance = new MeetingAttendanceForm();
        model.addAttribute("attendance", meetingAttendance);

        return "attendances";
    }

    @PostMapping
    public String saveOrUpdateMeetingAttendance(@ModelAttribute("attendance") @Valid MeetingAttendanceForm attendanceForm, BindingResult result, Errors errors, RedirectAttributes redirectAttributes) {

        Validator validator = new MeetingAttendanceValidator(messageSource);
        validator.validate(attendanceForm, errors);
        
        MeetingAttendance meetingAttendance = null;
        meetingAttendance = meetingAttendanceService.getByIdentifier(attendanceForm.getIdentifier());
        meetingAttendance = meetingAttendanceTransformer.toEntity(attendanceForm, meetingAttendance);
        meetingAttendance.setCongregation(congregationService.getCurrentCongregation());
        
        if(!result.hasErrors() && BooleanUtils.isTrue(meetingAttendance.getMemorial())){
            final Period attendancePeriod = PeriodBuilder.create().date(meetingAttendance.getDate()).build();
            final TheocraticYear year = new TheocraticYear(attendancePeriod);
            final MeetingAttendance memorialAttendance = meetingAttendanceService.getMemorialAttendance(congregationService.getCurrentCongregation(), year);
            
            if(memorialAttendance != null){
                final Date attendanceDate = Date.from(memorialAttendance.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                result.rejectValue("memorial", "validation.attendance.memorial", new Object[]{attendanceDate}, "validation.attendance.memorial.alt");
            }
        }

        if (result.hasErrors())
            return "attendances";

        AlertMessage message = null;
        try {
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

    private static List<String> convertArrayToList(final String[] array) {
        final List<String> values = Arrays.stream(array).collect(Collectors.toList());
        values.removeIf(String::isEmpty);

        return values.stream().map(item -> StringUtils.capitalize(item)).collect(Collectors.toList());
    }

    @Autowired
    public void setMonthAttendanceService(MonthAttendanceService monthAttendanceService) {
        this.monthAttendanceService = monthAttendanceService;
    }

    @Autowired
    public void setMonthAttendanceTransformer(MonthAttendanceTransformer monthAttendanceTransformer) {
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }

    @Autowired
    public void setGlobalAttendanceTransformer(GlobalAttendanceTransformer globalAttendanceTransformer) {
        this.globalAttendanceTransformer = globalAttendanceTransformer;
    }

    @Autowired
    public void setMeetingAttendanceService(MeetingAttendanceService meetingAttendanceService) {
        this.meetingAttendanceService = meetingAttendanceService;
    }

    @Autowired
    public void setYearAttendanceService(YearAttendanceService yearAttendanceService) {
        this.yearAttendanceService = yearAttendanceService;
    }

    @Autowired
    public void setCongregationService(CongregationService congregationService) {
        this.congregationService = congregationService;
    }

    @Autowired
    public void setMeetingAttendanceTransformer(MeetingAttendanceTransformer meetingAttendanceTransformer) {
        this.meetingAttendanceTransformer = meetingAttendanceTransformer;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
