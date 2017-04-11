package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.HighchartPoint;
import fr.gwombat.predicadmin.highchart.HighchartSerie;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gWombat
 *
 * @since 11/04/2017.
 */
@Component
public class YearAverageAttendanceTransformer extends AbstractHighchartDataTransformer<YearAttendanceVO> {

    @Override
    public List<HighchartSerie> convertToSeries(YearAttendanceVO yearAttendanceVo) {
        if(yearAttendanceVo != null) {
            List<HighchartSerie> series = new ArrayList<>(0);

            final HighchartSerie serie = new HighchartSerie();
            serie.setName("Average Attendance");

            for (MonthAttendanceVO monthAttendanceVo : yearAttendanceVo.getAttendances()) {
                if(monthAttendanceVo != null){
                    final HighchartPoint point = new HighchartPoint();
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
}
