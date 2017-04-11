package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.HighchartPoint;
import fr.gwombat.predicadmin.highchart.HighchartSerie;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import org.apache.tomcat.jni.Local;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@Component
public class HighchartTransformer {

    public List<HighchartSerie> toSeries(final MonthAttendanceVO monthAttendanceVo){
        final List<HighchartSerie> series = new ArrayList<>(0);

        final HighchartSerie serie1 = new HighchartSerie();
        serie1.setName(monthAttendanceVo.getPeriod().getEnd().getMonth().getDisplayName(TextStyle.FULL, LocaleContextHolder.getLocale()));

        final int year = monthAttendanceVo.getPeriod().getYear();
        final int month = monthAttendanceVo.getPeriod().getMonth();
        final int nbDaysInMonth = monthAttendanceVo.getPeriod().getEnd().getDayOfMonth();
        final Map<LocalDate, HighchartPoint> monthPoints = new HashMap<>(nbDaysInMonth);
        for(int i = 0; i < nbDaysInMonth; i++){
            final LocalDate date = LocalDate.of(year, month, i+1);
            final HighchartPoint point = new HighchartPoint();
            point.setX(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            monthPoints.put(date, point);
        }

        for(MeetingAttendanceVO meeting : monthAttendanceVo.getAttendances()){
            final HighchartPoint point = new HighchartPoint();
            point.setX(meeting.getDate().atTime(12, 0).atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
            point.setY(meeting.getAttendance());

            monthPoints.put(meeting.getDate(), point);
        }
        final List<HighchartPoint> serie1Points = monthPoints.entrySet().stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());
        serie1.setData(serie1Points);
        series.add(serie1);

        return series;
    }
}
