package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.*;
import fr.gwombat.predicadmin.highchart.enums.ChartType;
import fr.gwombat.predicadmin.highchart.enums.DashStyle;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.web.transformer.YearAttendanceTransformer;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by gWombat
 *
 * @since 11/04/2017.
 */
@Component
public class ChartYearAverageAttendanceCreator extends AbstractHighchartDataTransformer<List<YearAttendance>> {

    private YearAttendanceTransformer yearAttendanceTransformer;

    @Override
    protected List<Serie> createChartSeries(final List<YearAttendance> yearAttendances) {
        if(!CollectionUtils.isEmpty(yearAttendances)) {
            yearAttendances.sort(Comparator.comparing(YearAttendance::getTheocraticYear).reversed());

            final List<Serie> series = new ArrayList<>(1);

            for(int i = 0; i < Math.min(yearAttendances.size(), 5); i++) {
                final YearAttendance yearAttendance = yearAttendances.get(i);
                final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(yearAttendance);

                final AreaSerie serie = new AreaSerie(yearAttendance.getTheocraticYear().toString());
                serie.setLineWidth(1);
                if(i > 0)
                    serie.setVisible(false);


                if(!CollectionUtils.isEmpty(yearAttendanceVo.getAttendances())) {
                    final MonthAttendanceVO[] orderedAttendances = orderYearAttendances(yearAttendanceVo.getAttendances());
                    for(MonthAttendanceVO monthAttendanceVo : orderedAttendances) {
                        final Point point = new Point();
                        if(monthAttendanceVo != null)
                            point.setY(monthAttendanceVo.getAverageAttendance());
                        else
                            point.setY(null);
                        serie.addDataPoint(point);
                    }
                }
                series.add(serie);
            }
            return series;
        }
        return null;
    }

    @Override
    public ChartConfiguration createChartConfiguration(final List<YearAttendance> yearAttendances) {
        final ChartConfiguration chartConfig = new ChartConfiguration();

        chartConfig.getChart().setType(ChartType.AREASPLINE);
        chartConfig.getChart().setBackgroundColor("transparent");

        chartConfig.setTitle(null);

        final Crosshair crosshair = new Crosshair();
        crosshair.setDashStyle(DashStyle.DASH);
        crosshair.setWidth(3);
        crosshair.setColor("#383838");

        chartConfig.getTooltip().setShared(true);

        chartConfig.getXaxis().setTitle(null);
        chartConfig.getXaxis().setCrosshair(crosshair);
        chartConfig.getXaxis().setCategories(initCategories());

        chartConfig.getYaxis().setCrosshair(crosshair);
        chartConfig.getYaxis().setTitle(null);

        chartConfig.getExporting().setEnabled(false);
        chartConfig.getCredits().setEnabled(false);

        chartConfig.setSeries(createChartSeries(yearAttendances));

        return chartConfig;
    }

    private List<String> initCategories() {
        final List<String> categories = new ArrayList<>(12);

        for(int i = 0; i < 12; i++) {
            final Month month = Month.of(((i + 8) % 12) + 1);
            final String monthName = month.getDisplayName(TextStyle.SHORT, LocaleContextHolder.getLocale());
            categories.add(monthName);
        }

        return categories;
    }

    private MonthAttendanceVO[] orderYearAttendances(final List<MonthAttendanceVO> attendances) {
        final MonthAttendanceVO[] result = new MonthAttendanceVO[12];

        if(!CollectionUtils.isEmpty(attendances)) {
            for(MonthAttendanceVO monthAttendance : attendances) {
                if(monthAttendance != null)
                    result[(monthAttendance.getPeriod().getMonth() + 3) % 12] = monthAttendance;
            }
        }

        return result;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

}
