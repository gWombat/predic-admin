package fr.gwombat.predicadmin.highchart.transformer;

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
import fr.gwombat.predicadmin.model.MeetingAttendance;

/**
 * Created by gWombat
 * 
 * @since 11/04/2017.
 */
@Component
public class ChartGlobalAttendanceCreator extends AbstractHighchartDataTransformer<List<MeetingAttendance>> {

    private MessageSource messageSource;

    @Override
    protected List<Serie> createChartSeries(List<MeetingAttendance> attendances) {
        if (attendances != null) {
            final List<Serie> series = new ArrayList<>(0);
            final String serieName = messageSource.getMessage("chart.attendance", null, LocaleContextHolder.getLocale());
            final Serie serie = new Serie(serieName);
            final ZoneId utcZone = ZoneId.of("UTC");

            for (MeetingAttendance attendance : attendances) {
                if (attendance != null) {
                    final Point point = new Point();
                    final ZonedDateTime utcDateTime = ZonedDateTime.of(attendance.getDate().atStartOfDay(), utcZone);

                    point.setY(attendance.getAttendance());
                    point.setX(utcDateTime.toInstant().toEpochMilli());
                    serie.addDataPoint(point);
                }
            }
            series.add(serie);

            return series;
        }
        return null;
    }

    @Override
    public ChartConfiguration createChartConfiguration(List<MeetingAttendance> source) {
        final ChartConfiguration chartConfig = new ChartConfiguration();

        chartConfig.getChart().setType(ChartType.AREASPLINE);
        chartConfig.getChart().setBackgroundColor("transparent");

        chartConfig.setTitle(null);

        chartConfig.getxAxis().setType(AxisCategory.DATETIME);
        chartConfig.getxAxis().setTitle(null);

        chartConfig.getyAxis().setTitle(null);

        chartConfig.getExporting().setEnabled(false);
        chartConfig.getCredits().setEnabled(false);
        chartConfig.getLegend().setEnabled(false);

        chartConfig.setSeries(createChartSeries(source));

        return chartConfig;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
