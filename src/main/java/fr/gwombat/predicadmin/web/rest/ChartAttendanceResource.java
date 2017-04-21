package fr.gwombat.predicadmin.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gwombat.predicadmin.highchart.ChartConfiguration;
import fr.gwombat.predicadmin.highchart.transformer.ChartGlobalAttendanceCreator;
import fr.gwombat.predicadmin.highchart.transformer.ChartMonthAttendanceCreator;
import fr.gwombat.predicadmin.highchart.transformer.ChartYearAverageAttendanceCreator;
import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;

/**
 * Created by gWombat.
 *
 * @since 10/04/2017
 */
@RestController
@RequestMapping("/rest/chart/attendance")
public class ChartAttendanceResource {

    private ChartYearAverageAttendanceCreator chartYearAverageAttendanceCreator;
    private ChartMonthAttendanceCreator       chartMonthAttendanceCreator;
    private ChartGlobalAttendanceCreator      chartGlobalAttendanceCreator;

    private YearAttendanceService             yearAttendanceService;
    private MonthAttendanceService            monthAttendanceService;
    private CongregationService               congregationService;
    private MeetingAttendanceService          meetingAttendanceService;

    @GetMapping("/month")
    public ChartConfiguration chartResultMonthAttendance() {
        final Congregation currentCongregation = congregationService.getCurrentCongregation();
        final Period currentPeriod = PeriodBuilder.init().build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(currentCongregation, currentPeriod);
        final ChartConfiguration chartConfig = chartMonthAttendanceCreator.createChartConfiguration(monthAttendance);

        return chartConfig;
    }

    @GetMapping("/year")
    public ChartConfiguration chartAverageAttendanceYear() {
        final List<YearAttendance> yearAttendances = yearAttendanceService.getAttendancesForCongregation(congregationService.getCurrentCongregation());
        final ChartConfiguration chartConfig = chartYearAverageAttendanceCreator.createChartConfiguration(yearAttendances);

        return chartConfig;
    }

    @GetMapping
    public ChartConfiguration chartGlobalAttendance() {
        final List<MeetingAttendance> attendances = meetingAttendanceService.getByCongregation(congregationService.getCurrentCongregation());
        final ChartConfiguration chartConfig = chartGlobalAttendanceCreator.createChartConfiguration(attendances);

        return chartConfig;
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
    public void setHighchartTransformer(ChartMonthAttendanceCreator highchartTransformer) {
        this.chartMonthAttendanceCreator = highchartTransformer;
    }

    @Autowired
    public void setYearAverageAttendanceTransformer(ChartYearAverageAttendanceCreator chartYearAverageAttendanceCreator) {
        this.chartYearAverageAttendanceCreator = chartYearAverageAttendanceCreator;
    }

    @Autowired
    public void setYearAttendanceService(YearAttendanceService yearAttendanceService) {
        this.yearAttendanceService = yearAttendanceService;
    }

    @Autowired
    public void setMeetingAttendanceService(MeetingAttendanceService meetingAttendanceService) {
        this.meetingAttendanceService = meetingAttendanceService;
    }

    @Autowired
    public void setGlobalAttendanceTransformer(ChartGlobalAttendanceCreator chartGlobalAttendanceCreator) {
        this.chartGlobalAttendanceCreator = chartGlobalAttendanceCreator;
    }
}
