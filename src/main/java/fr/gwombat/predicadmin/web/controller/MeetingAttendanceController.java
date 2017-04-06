package fr.gwombat.predicadmin.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;
import fr.gwombat.predicadmin.web.transformer.MeetingAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;

@Controller
@RequestMapping("/attendance")
public class MeetingAttendanceController {
    
    private final MeetingAttendanceService meetingAttendanceService;
    private final MonthAttendanceService monthAttendanceService;
    private final CongregationService congregationService;
    
    private final MeetingAttendanceTransformer meetingAttendanceTransformer;
    private final MonthAttendanceTransformer monthAttendanceTransformer;
    
    @Autowired
    public MeetingAttendanceController(final MeetingAttendanceService meetingAttendanceService,
            final MeetingAttendanceTransformer meetingAttendancetransformer,
            final CongregationService congregationService,
            final MonthAttendanceService monthAttendanceService,
            final MonthAttendanceTransformer monthAttendanceTransformer) {
        this.meetingAttendanceService = meetingAttendanceService;
        this.meetingAttendanceTransformer = meetingAttendancetransformer;
        this.congregationService = congregationService;
        this.monthAttendanceService = monthAttendanceService;
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }
    
    @GetMapping
    public String attendancePage(Model model){
        final MeetingAttendanceForm meetingAttendance = new MeetingAttendanceForm();
        model.addAttribute("attendance", meetingAttendance);
        
        final Period period = PeriodBuilder.init().month(4).year(2017).build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(congregationService.getCurrentCongregation(), period);
        final MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
        model.addAttribute("monthAttendance", monthAttendanceVo);
        
        return "attendances";
    }
    
    @PostMapping
    public String saveOrUpdateMeetingAttendance(@ModelAttribute("attendance") @Valid MeetingAttendanceForm attendanceForm,
            BindingResult result){
        if(result.hasErrors())
            return null;
        
        MeetingAttendance meetingAttendance = meetingAttendanceService.getByIdentifier(attendanceForm.getIdentifier());
        meetingAttendance = meetingAttendanceTransformer.toEntity(attendanceForm, meetingAttendance);
        meetingAttendance.setCongregation(congregationService.getCurrentCongregation());
        meetingAttendanceService.save(meetingAttendance);
        
        return "redirect:/attendance";
    }

}
