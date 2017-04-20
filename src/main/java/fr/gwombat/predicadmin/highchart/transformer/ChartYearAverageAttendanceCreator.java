package fr.gwombat.predicadmin.highchart.transformer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.highchart.AreaSerie;
import fr.gwombat.predicadmin.highchart.Crosshair;
import fr.gwombat.predicadmin.highchart.ChartConfiguration;
import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.Serie;
import fr.gwombat.predicadmin.highchart.enums.AxisCategory;
import fr.gwombat.predicadmin.highchart.enums.ChartType;
import fr.gwombat.predicadmin.highchart.enums.DashStyle;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;
import fr.gwombat.predicadmin.web.transformer.YearAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

/**
 * Created by gWombat
 *
 * @since 11/04/2017.
 */
@Component
public class ChartYearAverageAttendanceCreator extends AbstractHighchartDataTransformer<YearAttendance> {

    private MessageSource             messageSource;
    private YearAttendanceTransformer yearAttendanceTransformer;

    @Override
    protected List<Serie> createChartSeries(final YearAttendance yearAttendance) {
        if (yearAttendance != null) {
            final List<Serie> series = new ArrayList<>(1);

            final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(yearAttendance);

            final String serieName = messageSource.getMessage("chart.average.attendance", null, LocaleContextHolder.getLocale());
            final AreaSerie serie = new AreaSerie(serieName);
            serie.setLineWidth(1);
            serie.setPointInterval(1000 * 3600 * 24 * 30);

            final ZoneId utcZone = ZoneId.of("UTC");
            Period currentPeriod = PeriodBuilder.init().year(yearAttendanceVo.getYear() - 1).month(9).build();

            // 12 months in a year (starting with September)
            for (int i = 0; i < 12; i++) {
                MonthAttendanceVO monthAttendanceVo = null;
                if (!yearAttendanceVo.getAttendances().isEmpty() && i < yearAttendanceVo.getAttendances().size()) {
                    // while a month attendance is registered with data
                    //(<=> yearAttendanceVo.getAttendances().get(i) is not null)
                    monthAttendanceVo = yearAttendanceVo.getAttendances().get(i);
                    currentPeriod = monthAttendanceVo.getPeriod();
                } else
                    currentPeriod = Period.shiftPeriod(currentPeriod, 1);

                final ZonedDateTime utcDateTime = ZonedDateTime.of(currentPeriod.getStart(), utcZone);

                final Point point = new Point();
                point.setX(utcDateTime.toInstant().toEpochMilli());
                // if we have attendance for the month i:
                if (monthAttendanceVo != null)
                    point.setY(monthAttendanceVo.getAverageAttendance());

                serie.addDataPoint(point);
            }

            series.add(serie);

            return series;
        }
        return null;
    }

    @Override
    public ChartConfiguration createChartConfiguration(final YearAttendance yearAttendance) {
        final ChartConfiguration chartConfig = new ChartConfiguration();

        chartConfig.getChart().setType(ChartType.AREASPLINE);
        chartConfig.getChart().setBackgroundColor("transparent");

        chartConfig.setTitle(null);

        final Crosshair crosshair = new Crosshair();
        crosshair.setDashStyle(DashStyle.DASH);
        crosshair.setWidth(3);
        crosshair.setColor("#383838");

        chartConfig.getXaxis().setType(AxisCategory.DATETIME);
        chartConfig.getXaxis().setTitle(null);
        chartConfig.getXaxis().setCrosshair(crosshair);
        chartConfig.getXaxis().getDateTimeLabelFormats().setMonth("%b");

        chartConfig.getYaxis().setCrosshair(crosshair);
        chartConfig.getYaxis().setTitle(null);

        chartConfig.getExporting().setEnabled(false);
        chartConfig.getCredits().setEnabled(false);
        chartConfig.getLegend().setEnabled(false);

        chartConfig.setSeries(createChartSeries(yearAttendance));

        return chartConfig;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

}
