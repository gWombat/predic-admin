package fr.gwombat.predicadmin.highchart.transformer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.highchart.ChartConfiguration;
import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.Serie;
import fr.gwombat.predicadmin.highchart.enums.AxisCategory;
import fr.gwombat.predicadmin.highchart.enums.ChartType;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@Component
public class ChartMonthAttendanceCreator extends AbstractHighchartDataTransformer<MonthAttendance> {

    private MonthAttendanceTransformer monthAttendanceTransformer;
    private MessageSource              messageSource;

    protected List<Serie> createChartSeries(final MonthAttendance monthAttendance) {
        final List<Serie> series = new ArrayList<>(0);

        if (monthAttendance != null) {
            MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);

            final Serie serie1 = new Serie();
            final String serieName = messageSource.getMessage("chart.attendance", null, LocaleContextHolder.getLocale());
            serie1.setName(serieName);

            final int year = monthAttendanceVo.getPeriod().getYear();
            final int month = monthAttendanceVo.getPeriod().getMonth();
            final int nbDaysInMonth = monthAttendanceVo.getPeriod().getEnd().getDayOfMonth();
            final List<Point> points = new ArrayList<>(0);
            final ZoneId utcZone = ZoneId.of("UTC");

            int lastIndexFound = 0;
            for (int i = 0; i < nbDaysInMonth; i++) {
                final LocalDate date = LocalDate.of(year, month, i + 1);
                final Point point = new Point();
                final ZonedDateTime utcDateTime = ZonedDateTime.of(date.atStartOfDay(), utcZone);
                MeetingAttendanceVO attendanceToAdd = null;

                for (int j = lastIndexFound; j < monthAttendanceVo.getAttendances().size(); j++) {
                    if (monthAttendanceVo.getAttendances().get(j).getDate().equals(date)) {
                        attendanceToAdd = monthAttendanceVo.getAttendances().get(j);
                        lastIndexFound = j;
                        break;
                    }
                }

                point.setX(utcDateTime.toInstant().toEpochMilli());
                if (attendanceToAdd != null)
                    point.setY(attendanceToAdd.getAttendance());

                points.add(point);
            }
            serie1.setData(points);
            series.add(serie1);
        }

        return series;
    }

    @Override
    public ChartConfiguration createChartConfiguration(MonthAttendance monthAttendanceVo) {
        final ChartConfiguration chartConfig = new ChartConfiguration();
        chartConfig.getChart().setType(ChartType.COLUMN);

        chartConfig.setTitle(null);

        chartConfig.getxAxis().setType(AxisCategory.DATETIME);
        chartConfig.getxAxis().setCrosshair(true);
        chartConfig.getxAxis().setTitle(null);

        chartConfig.getyAxis().setTitle(null);
        chartConfig.getCredits().setEnabled(false);
        chartConfig.getLegend().setEnabled(false);
        chartConfig.getExporting().setEnabled(false);

        chartConfig.getPlotOptions().getColumn().setBorderWidth(0);
        chartConfig.getPlotOptions().getColumn().setGroupPadding(0.2);
        chartConfig.getPlotOptions().getColumn().setPointPadding(0.2);

        chartConfig.setSeries(createChartSeries(monthAttendanceVo));

        return chartConfig;
    }

    @Autowired
    public void setMonthAttendanceTransformer(MonthAttendanceTransformer monthAttendanceTransformer) {
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
