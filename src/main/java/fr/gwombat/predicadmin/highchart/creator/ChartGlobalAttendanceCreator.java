package fr.gwombat.predicadmin.highchart.creator;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.highchart.ChartConfiguration;
import fr.gwombat.predicadmin.highchart.Crosshair;
import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.enums.AxisCategory;
import fr.gwombat.predicadmin.highchart.enums.ChartType;
import fr.gwombat.predicadmin.highchart.enums.DashStyle;
import fr.gwombat.predicadmin.highchart.serie.LineSerie;
import fr.gwombat.predicadmin.highchart.serie.Serie;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.web.design.Color;
import fr.gwombat.predicadmin.web.transformer.MonthAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;

/**
 * Created by gWombat
 * 
 * @since 11/04/2017.
 */
@Component
public class ChartGlobalAttendanceCreator extends AbstractHighchartDataTransformer<List<MonthAttendance>> {

    private static final ZoneId        UTC_ZONE = ZoneId.of("UTC");

    private MessageSource              messageSource;
    private MonthAttendanceTransformer monthAttendanceTransformer;

    @Override
    protected List<Serie> createChartSeries(List<MonthAttendance> attendances) {
        if (attendances != null) {
            final List<Serie> series = new ArrayList<>(0);
            final String serieName = messageSource.getMessage("chart.attendance", null, LocaleContextHolder.getLocale());
            final LineSerie serie = new LineSerie(serieName);
            serie.setLineWidth(1);

            for (MonthAttendance attendance : attendances) {
                if (attendance != null) {
                    final MonthAttendanceVO monthAttendanceVo = monthAttendanceTransformer.toViewObject(attendance);

                    final Point point = new Point();
                    final ZonedDateTime utcDateTime = ZonedDateTime.of(monthAttendanceVo.getPeriod().getStart(), UTC_ZONE);

                    point.setY(monthAttendanceVo.getAverageAttendance());
                    point.setX(utcDateTime.toInstant().toEpochMilli());
                    if (monthAttendanceVo.getMemorialAttendance() != null)
                        point.setColor(Color.MEMORIAL.getHexaColor());

                    serie.addDataPoint(point);
                }
            }
            series.add(serie);

            return series;
        }
        return null;
    }

    @Override
    public ChartConfiguration createChartConfiguration(List<MonthAttendance> source) {
        final ChartConfiguration chartConfig = new ChartConfiguration();

        chartConfig.getChart().setType(ChartType.SPLINE);

        chartConfig.setTitle(null);

        final Crosshair crosshair = new Crosshair();
        crosshair.setDashStyle(DashStyle.DASH);
        crosshair.setWidth(3);
        crosshair.setColor("#383838");

        chartConfig.getXaxis().setType(AxisCategory.DATETIME);
        chartConfig.getXaxis().setTitle(null);
        chartConfig.getXaxis().setCrosshair(crosshair);

        chartConfig.getYaxis().setTitle(null);
        chartConfig.getYaxis().setCrosshair(crosshair);

        chartConfig.getPlotOptions().getColumn().setBorderWidth(0);

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

    @Autowired
    public void setMonthAttendanceTransformer(MonthAttendanceTransformer monthAttendanceTransformer) {
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }
}
