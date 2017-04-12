package fr.gwombat.predicadmin.web.rest;

import fr.gwombat.predicadmin.highchart.HighchartOptions;
import fr.gwombat.predicadmin.highchart.Serie;
import fr.gwombat.predicadmin.highchart.transformer.GlobalAttendanceTransformer;
import fr.gwombat.predicadmin.highchart.transformer.HighchartMonthAttendanceTransformer;
import fr.gwombat.predicadmin.highchart.transformer.YearAverageAttendanceTransformer;
import fr.gwombat.predicadmin.model.*;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.transformer.YearAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gWombat.
 *
 * @since 10/04/2017
 */
@RestController
@RequestMapping("/rest/chart/attendance")
public class ChartAttendanceResource {

    private MonthAttendanceTransformer          monthAttendanceTransformer;
    private YearAttendanceTransformer           yearAttendanceTransformer;
    private YearAverageAttendanceTransformer    yearAverageAttendanceTransformer;
    private HighchartMonthAttendanceTransformer highchartTransformer;
    private GlobalAttendanceTransformer         globalAttendanceTransformer;

    private YearAttendanceService    yearAttendanceService;
    private MonthAttendanceService   monthAttendanceService;
    private CongregationService      congregationService;
    private MeetingAttendanceService meetingAttendanceService;


    @GetMapping("/month")
    public List<Serie> chartResultMonthAttendance() {
        final Congregation currentCongregation = congregationService.getCurrentCongregation();
        final Period currentPeriod = PeriodBuilder.init().build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(currentCongregation, currentPeriod);
        final MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
        final List<Serie> series = highchartTransformer.convertToSeries(monthAttendanceVo);

        return series;
    }

    @GetMapping("/year")
    //public List<Serie> chartAverageAttendanceYear() {
    public HighchartOptions chartAverageAttendanceYear() {
        final TheocraticYear year = new TheocraticYear(2017);
        final YearAttendance yearAttendance = yearAttendanceService.getAttendanceForYear(congregationService.getCurrentCongregation(), year);
        final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(yearAttendance);
        //final List<Serie> series = yearAverageAttendanceTransformer.convertToSeries(yearAttendanceVo);
        
        final HighchartOptions chartOptions = yearAverageAttendanceTransformer.createChartOptions(yearAttendanceVo);

        return chartOptions;
    }

    @GetMapping
    public List<Serie> chartGlobalAttendance() {
        final List<MeetingAttendance> attendances = meetingAttendanceService.getByCongregation(congregationService.getCurrentCongregation());
        final List<Serie> series = globalAttendanceTransformer.convertToSeries(attendances);

        return series;
    }

    @Autowired
    public void setMonthAttendanceTransformer(MonthAttendanceTransformer monthAttendanceTransformer) {
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }

    @Autowired
    public void setMonthAttendanceService(MonthAttendanceService monthAttendanceService) {
        this.monthAttendanceService = monthAttendanceService;
    }

    @Autowired
    public void setCongregationService(CongregationService congregationService) {
        this.congregationService = congregationService;
    }

    @Autowired
    public void setHighchartTransformer(HighchartMonthAttendanceTransformer highchartTransformer) {
        this.highchartTransformer = highchartTransformer;
    }

    @Autowired
    public void setYearAverageAttendanceTransformer(YearAverageAttendanceTransformer yearAverageAttendanceTransformer) {
        this.yearAverageAttendanceTransformer = yearAverageAttendanceTransformer;
    }

    @Autowired
    public void setYearAttendanceService(YearAttendanceService yearAttendanceService) {
        this.yearAttendanceService = yearAttendanceService;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

    @Autowired
    public void setMeetingAttendanceService(MeetingAttendanceService meetingAttendanceService) {
        this.meetingAttendanceService = meetingAttendanceService;
    }

    @Autowired
    public void setGlobalAttendanceTransformer(GlobalAttendanceTransformer globalAttendanceTransformer) {
        this.globalAttendanceTransformer = globalAttendanceTransformer;
    }
}
