package fr.gwombat.predicadmin.highchart.transformer;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.highchart.AreaSerie;
import fr.gwombat.predicadmin.highchart.Crosshair;
import fr.gwombat.predicadmin.highchart.HighchartOptions;
import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.Serie;
import fr.gwombat.predicadmin.highchart.enums.AxisCategory;
import fr.gwombat.predicadmin.highchart.enums.ChartType;
import fr.gwombat.predicadmin.highchart.enums.DashStyle;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

/**
 * Created by gWombat
 *
 * @since 11/04/2017.
 */
@Component
public class YearAverageAttendanceTransformer extends AbstractHighchartDataTransformer<YearAttendanceVO> {
    
    private MessageSource messageSource;

    @Override
    public List<Serie> convertToSeries(final YearAttendanceVO yearAttendanceVo) {
        if(yearAttendanceVo != null) {
            List<Serie> series = new ArrayList<>(0);

            final String serieName = messageSource.getMessage("chart.average.attendance", null, LocaleContextHolder.getLocale());
            final AreaSerie serie = new AreaSerie(serieName);
            serie.setLineWidth(1);
            serie.setPointInterval(1000 * 3600 * 24 * 30);

            for (MonthAttendanceVO monthAttendanceVo : yearAttendanceVo.getAttendances()) {
                if(monthAttendanceVo != null){
                    final Point point = new Point();
                    point.setX(monthAttendanceVo.getPeriod().getStart().atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
                    point.setY(monthAttendanceVo.getAverageAttendance());
                    serie.addData(point);
                }
            }
            series.add(serie);

            return series;
        }
        return null;
    }
    
    public HighchartOptions createChartOptions(final YearAttendanceVO yearAttendanceVo){
        final HighchartOptions chartOptions = new HighchartOptions();
        
        chartOptions.getChart().setType(ChartType.AREA);
        chartOptions.getChart().setBackgroundColor("transparent");
        
        chartOptions.setTitle(null);
        
        final Crosshair crosshair = new Crosshair();
        crosshair.setDashStyle(DashStyle.DASH);
        crosshair.setWidth(3);
        crosshair.setColor("#383838");
        
        chartOptions.getxAxis().setType(AxisCategory.DATETIME);
        chartOptions.getxAxis().setTitle(null);
        chartOptions.getxAxis().setCrosshair(crosshair);
        //chartOptions.getxAxis().setTickInterval(1000 * 3600 * 24 * 15);
        chartOptions.getxAxis().getDateTimeLabelFormats().setMonth("%b");
        
        chartOptions.getyAxis().setCrosshair(crosshair);
        chartOptions.getyAxis().setTitle(null);
        
        chartOptions.getExporting().setEnabled(false);
        chartOptions.getCredits().setEnabled(false);
        chartOptions.getLegend().setEnabled(false);
        
        chartOptions.setSeries(convertToSeries(yearAttendanceVo));
        
        return chartOptions;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
}
