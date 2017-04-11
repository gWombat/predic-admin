package fr.gwombat.predicadmin.web.rest;

import fr.gwombat.predicadmin.highchart.HighchartSerie;
import fr.gwombat.predicadmin.highchart.transformer.HighchartTransformer;
import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by gWombat.
 *
 * @since 10/04/2017
 */
@RestController
@RequestMapping("/rest/chart/attendance")
public class ChartAttendanceResource {

    private MonthAttendanceTransformer monthAttendanceTransformer;
    private MonthAttendanceService     monthAttendanceService;
    private CongregationService        congregationService;
    private HighchartTransformer       highchartTransformer;

    @GetMapping
    public List<HighchartSerie> chartResultMonthAttendance() {
        final Congregation currentCongregation = congregationService.getCurrentCongregation();
        final Period currentPeriod = PeriodBuilder.init().build();
        final MonthAttendance monthAttendance = monthAttendanceService.getByPeriod(currentCongregation, currentPeriod);
        final MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
        final List<HighchartSerie> series = highchartTransformer.toSeries(monthAttendanceVo);

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
    public void setHighchartTransformer(HighchartTransformer highchartTransformer) {
        this.highchartTransformer = highchartTransformer;
    }
}
